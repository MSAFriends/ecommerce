package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class OrderResponse {
    private Long id;
    private Long memberId;
    private OrderStatus status;
    private PriceResponse price;
    private String request;
    private RecipientResponse recipient;
    private List<OrderItemResponse> orderItems;

    @Builder
    public OrderResponse(Long id, Long memberId, OrderStatus status, PriceResponse price, String request, RecipientResponse recipient, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.memberId = memberId;
        this.status = status;
        this.price = price;
        this.request = request;
        this.recipient = recipient;
        this.orderItems = orderItems;
    }

    public static OrderResponse from(final Order order) {
        return from(order, Collections.emptyList());
    }

    public static OrderResponse from(final Order order, final List<CouponResponse> coupons) {
        return OrderResponse.builder()
                .id(order.getId())
                .memberId(order.getMemberId())
                .status(order.getStatus())
                .request(order.getRequest())
                .recipient(RecipientResponse.from(order.getRecipient()))
                .price(PriceResponse.from(order, coupons))
                .orderItems(OrderItemResponse.from(order.getOrderItems()))
                .build();
    }
}
