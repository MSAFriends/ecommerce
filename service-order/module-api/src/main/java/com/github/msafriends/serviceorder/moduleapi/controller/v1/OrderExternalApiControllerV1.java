package com.github.msafriends.serviceorder.moduleapi.controller.v1;

import com.github.msafriends.serviceorder.moduleapi.service.OrderService;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderExternalApiControllerV1 {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@RequestHeader("Member-Id") Long memberId) {
        return ResponseEntity.ok(orderService.getAllOrdersByMemberId(memberId));
    }
}
