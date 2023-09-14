package com.github.msafriends.serviceproduct.modulecore.dto.product;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularProductResponse {
    private Long productId;
    private String name;
    private Integer quantity;
    private Integer discount;
    private AgeLimit ageLimit;
    private float buySatisfy;

    @Builder(access = AccessLevel.PRIVATE)
    private PopularProductResponse(Long productId, String name, Integer quantity, Integer discount, AgeLimit ageLimit, float buySatisfy) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.discount = discount;
        this.ageLimit = ageLimit;
        this.buySatisfy = buySatisfy;
    }

    public static PopularProductResponse from(Product product){
        return PopularProductResponse
            .builder()
            .productId(product.getId())
            .name(product.getName())
            .ageLimit(product.getAgeLimit())
            .quantity(product.getQuantity())
            .discount(product.getBenefit().getDiscount())
            .buySatisfy(product.getBuySatisfy())
            .build();
    }
}
