package com.github.msafriends.serviceorder.moduleapi.service;

import com.github.msafriends.serviceorder.moduleapi.repository.OrderRepository;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

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
    public Long addCartItemToOrder(Long memberId, UpdateCartItemRequest request) {
        Order pendingOrder = getOrCreatePendingOrder(memberId);
        pendingOrder.updateCartItem(request);
        return orderRepository.save(pendingOrder).getId();
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private Order getOrCreatePendingOrder(Long memberId) {
        Optional<Order> pendingOrder = orderRepository.findPendingOrder(memberId);
        return pendingOrder.orElseGet(() -> Order.createPendingOrderBuilder()
                .memberId(memberId)
                .build());
    }
}
