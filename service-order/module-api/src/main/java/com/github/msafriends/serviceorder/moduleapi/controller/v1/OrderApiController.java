package com.github.msafriends.serviceorder.moduleapi.controller.v1;

import com.github.msafriends.serviceorder.modulecore.dto.request.order.OrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import com.github.msafriends.serviceorder.moduleapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/v1")
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("/members/{memberId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(orderService.getOrdersByMemberId(memberId));
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> createOrder(
            @RequestHeader("Member-Id") Long memberId,
            @Valid @RequestBody OrderRequest orderRequest
    ) {
        return ResponseEntity.created(URI.create("/api/internal/v1/orders/" + orderService.createOrder(memberId, orderRequest))).build();
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
