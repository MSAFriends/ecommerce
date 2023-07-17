package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private Integer quantity;
    private Integer price;

    @Builder
    public ProductResponse(Long id, Integer quantity, Integer price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public static ProductResponse from(final Product product) {
        return ProductResponse.builder()
                .id(product.getProductId())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
