package com.github.msafriends.serviceproduct.modulecore.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStockRequest {
    @NotNull
    private Long productId;
    private int quantity;

    @Builder
    public UpdateStockRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}