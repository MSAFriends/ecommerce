package com.github.msafriends.serviceproduct.modulecore.dto.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEvent {
    Long productId;

    @Builder
    public ProductEvent(Long productId) {
        this.productId = productId;
    }
}
