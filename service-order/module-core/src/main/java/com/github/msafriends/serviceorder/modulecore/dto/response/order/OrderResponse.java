package com.github.msafriends.serviceorder.modulecore.dto.response.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponse {
    private final Long id;
    private final Long memberId;
    private final OrderStatus status;
    private final PriceResponse price;
    private final String request;
    private final RecipientResponse recipient;
    private final List<OrderItemResponse> orderItems;

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
        return OrderResponse.builder()
                .id(order.getId())
                .memberId(order.getMemberId())
                .status(order.getStatus())
                .request(order.getRequest())
                .recipient(RecipientResponse.from(order.getRecipient()))
                .price(PriceResponse.from(order))
                .orderItems(OrderItemResponse.from(order.getOrderItems()))
                .build();
    }
}
