package com.github.msafriends.serviceorder.modulecore.dto.response.order;

import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    private final Integer quantity;
    private final Integer price;

    @Builder
    public ProductResponse(Long id, Integer quantity, Integer price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public static ProductResponse from(final Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
