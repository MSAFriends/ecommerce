package com.github.msafriends.serviceorder.moduleapi.controller.v1;

import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderCartResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import com.github.msafriends.serviceorder.moduleapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/v1")
public class OrderInternalApiControllerV1 {
    private final OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("/members/{memberId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(orderService.getOrdersByMemberId(memberId));
    }

    @GetMapping("/orders/carts")
    public ResponseEntity<Optional<OrderCartResponse>> getCartItems(@RequestHeader("Member-Id") Long memberId) {
        return ResponseEntity.ok(orderService.getCartItemsByMemberId(memberId));
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> addCartItemToOrder(
            @RequestHeader("Member-Id") Long memberId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        return ResponseEntity.created(URI.create("/api/internal/v1/orders/" + orderService.addCartItemToOrder(memberId, request))).build();
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
