package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartItemRequest {
    private final Long productId;

    @Min(1)
    private final Integer quantity;

    @Builder
    public CartItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
