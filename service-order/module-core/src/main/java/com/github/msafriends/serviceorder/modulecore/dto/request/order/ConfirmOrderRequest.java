package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfirmOrderRequest {
    private final String request;

    private final List<Long> orderCouponIds;

    @NotNull
    private final RecipientRequest recipient;

    @Builder
    public ConfirmOrderRequest(String request, List<Long> orderCouponIds, RecipientRequest recipient) {
        this.request = request;
        this.orderCouponIds = orderCouponIds;
        this.recipient = recipient;
    }
}