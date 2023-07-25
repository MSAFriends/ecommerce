package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private final String request;

    private final List<OrderItemRequest> orderItems;

    private final List<Long> orderCouponIds;

    private final RecipientRequest recipient;

    @Builder
    public OrderRequest(String request, List<OrderItemRequest> orderItems, List<Long> orderCouponIds, RecipientRequest recipient) {
        this.request = request;
        this.orderItems = orderItems;
        this.orderCouponIds = orderCouponIds;
        this.recipient = recipient;
    }
}