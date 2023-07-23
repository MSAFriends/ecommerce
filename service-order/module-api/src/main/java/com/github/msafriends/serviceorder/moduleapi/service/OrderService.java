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
        // 1. 가격을 알아내기 위해서 상품 ID마다 상품 서비스로 상품 정보를 요청한다.
        // 1-1. 해당 상품이 존재하는가?
        // 1-2. 재고가 충분한가?
        List<Product> products = getProducts(orderRequest);
        validateIfProductStockIsEnough(products);

        // 2. 주문을 생성한다.
        Order order = Order.builder()
                .memberId(memberId)
                .status(OrderStatus.APPROVED)
                .request(orderRequest.getRequest())
                .products(products)
                .recipient(RecipientRequest.toRecipient(orderRequest.getRecipient()))
                .build();

        // 3. 멤버 서비스로부터 사용자가 사용할 수 있는 쿠폰 목록을 받아오고 검증한다.
        // 3-1. 쿠폰이 존재하는가?
        // 3-2. 쿠폰이 사용 가능한가? --> 항상 사용 가능한 쿠폰이 들어온다고 가정함
        List<OrderCoupon> availableCoupons = memberServiceClient.getAvailableCouponsByMemberId(memberId).stream()
                .map(couponResponse -> OrderCouponResponse.toCoupon(order, couponResponse))
                .toList();
        validateIfCouponExists(orderRequest, availableCoupons);

        // 4. 주문 쿠폰을 생성한다.
        order.addCoupons(availableCoupons);

        // 5. 재고를 차감한다.
        return orderRepository.save(order).getId();
    }

    private void validateIfCouponExists(OrderRequest orderRequest, List<OrderCoupon> availableCoupons) {
        orderRequest.getOrderCouponIds().forEach(orderCouponId -> {
            if (availableCoupons.stream().noneMatch(availableCoupon -> availableCoupon.getCouponId().equals(orderCouponId))) {
                throw new RuntimeException(String.format("Coupon with ID %d not found", orderCouponId));
            }
        });
    }

    private void validateIfProductStockIsEnough(List<Product> products) {
        products.forEach(product -> {
            if (product.getQuantity() < 1) {
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
