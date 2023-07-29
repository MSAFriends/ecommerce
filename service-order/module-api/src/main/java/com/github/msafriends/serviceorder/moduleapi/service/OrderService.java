package com.github.msafriends.serviceorder.moduleapi.service;

import com.github.msafriends.serviceorder.moduleapi.client.MemberServiceClient;
import com.github.msafriends.serviceorder.modulecore.repository.OrderRepository;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.ConfirmOrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.coupon.OrderCouponResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.PendingOrderResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberServiceClient memberServiceClient;

    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::from)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderResponse> getOrderDetailsAwaitingAcceptance(Long memberId) {
        return orderRepository.findAllByMemberIdAndStatus(memberId, OrderStatus.AWAITING_ACCEPTANCE).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public List<OrderResponse> getAllOrdersByMemberId(Long memberId) {
        return orderRepository.findAllByMemberId(memberId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public Optional<PendingOrderResponse> getPendingOrderByMemberId(Long memberId) {
        return orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)
                .map(PendingOrderResponse::from);
    }

    @Transactional
    public Long addCartItemToOrder(Long memberId, UpdateCartItemRequest request) {
        Order pendingOrder = getOrCreatePendingOrder(memberId);
        pendingOrder.updateCartItem(request);
        return orderRepository.save(pendingOrder).getId();
    }

    public void confirmOrder(Long memberId, ConfirmOrderRequest request) {
        Order order = orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.confirm(request);
        applyCoupons(order, request.getOrderCouponIds());
        orderRepository.save(order);
    }

    private void applyCoupons(Order order, List<Long> orderCouponIds) {
        List<OrderCouponResponse> availableCoupons = memberServiceClient.getAvailableCouponsByMemberId(order.getMemberId());
        List<OrderCoupon> appliedCoupons = new ArrayList<>();

        orderCouponIds.forEach(couponId -> {
            OrderCouponResponse coupon = availableCoupons.stream()
                    .filter(c -> c.getId().equals(couponId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));
            appliedCoupons.add(OrderCouponResponse.toCoupon(order, coupon));
        });
        order.addCoupons(appliedCoupons);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private Order getOrCreatePendingOrder(Long memberId) {
        Optional<Order> pendingOrder = orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING);
        return pendingOrder.orElseGet(() -> Order.createPendingOrderBuilder()
                .memberId(memberId)
                .build());
    }
}
