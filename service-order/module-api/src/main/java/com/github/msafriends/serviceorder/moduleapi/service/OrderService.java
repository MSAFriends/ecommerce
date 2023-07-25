package com.github.msafriends.serviceorder.moduleapi.service;

import com.github.msafriends.serviceorder.moduleapi.client.MemberServiceClient;
import com.github.msafriends.serviceorder.moduleapi.client.ProductServiceClient;
import com.github.msafriends.serviceorder.moduleapi.repository.OrderRepository;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.OrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.RecipientRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.coupon.OrderCouponResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberServiceClient memberServiceClient;
    private final ProductServiceClient productServiceClient;

    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::from)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderResponse> getOrdersByMemberId(Long memberId) {
        return orderRepository.findAllByMemberId(memberId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional
    public Long createOrder(Long memberId, OrderRequest orderRequest) {
        List<Product> products = getProducts(orderRequest);
        validateIfProductStockIsEnough(orderRequest, products);

        Order order = Order.builder()
                .memberId(memberId)
                .status(OrderStatus.CREATE_PENDING)
                .request(orderRequest.getRequest())
                .products(products)
                .recipient(RecipientRequest.toRecipient(orderRequest.getRecipient()))
                .build();

        List<OrderCoupon> availableCoupons = OrderCouponResponse.toCoupons(order, memberServiceClient.getAvailableCouponsByMemberId(memberId));
        validateIfCouponExists(orderRequest, availableCoupons);
        order.addCoupons(availableCoupons);

        return orderRepository.save(order).getId();
    }

    private void validateIfCouponExists(OrderRequest orderRequest, List<OrderCoupon> availableCoupons) {
        orderRequest.getOrderCouponIds().forEach(orderCouponId -> {
            if (availableCoupons.stream().noneMatch(availableCoupon -> availableCoupon.getCouponId().equals(orderCouponId))) {
                throw new RuntimeException(String.format("Coupon with ID %d not found", orderCouponId));
            }
        });
    }

    private void validateIfProductStockIsEnough(OrderRequest orderRequest, List<Product> products) {
        orderRequest.getOrderItems().forEach(orderItemRequest -> {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(orderItemRequest.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format("Product with ID %d not found", orderItemRequest.getProductId())));

            if (product.getQuantity() < orderItemRequest.getQuantity()) {
                throw new RuntimeException(String.format("Product stock for product ID %d is not enough", product.getId()));
            }
        });
    }

    private List<Product> getProducts(OrderRequest orderRequest) {
        return orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> ProductResponse.toProduct(productServiceClient.getProduct(orderItemRequest.getProductId())))
                .toList();
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
