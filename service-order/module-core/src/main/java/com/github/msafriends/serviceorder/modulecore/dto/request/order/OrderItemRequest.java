package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemRequest {
    private final Long productId;

    @Min(1)
    private final Integer quantity;

    @Builder
    public OrderItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
