package com.github.msafriends.serviceproduct.modulecore.dto.product;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponse {
    private Long productId;
    private Long sellerId;
    private Long code;
    private String name;
    private int price;
    private int salesPrice;
    private Integer quantity;
    private String delivery;
    private Integer buySatisfy;
    private Integer discount;
    private Integer mileage;
    private AgeLimit ageLimit;
    private Size size;
    private Long categoryId;

    @Builder
    public ProductResponse(Long productId, Long sellerId, Long code, String name, int price, int salesPrice,
        Integer quantity, String delivery, Integer buySatisfy, Integer discount, Integer mileage, AgeLimit ageLimit,
        Size size, Long categoryId) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.code = code;
        this.name = name;
        this.price = price;
        this.salesPrice = salesPrice;
        this.quantity = quantity;
        this.delivery = delivery;
        this.buySatisfy = buySatisfy;
        this.discount = discount;
        this.mileage = mileage;
        this.ageLimit = ageLimit;
        this.size = size;
        this.categoryId = categoryId;
    }

    public static ProductResponse from(Product product){
        ProductResponseBuilder builder = ProductResponse.builder()
            .productId(product.getId())
            .name(product.getName())
            .price(product.getPrice().getPriceValue())
            .salesPrice(product.getPrice().getSalePriceValue())
            .quantity(product.getQuantity())
            .code(product.getCode())
            .mileage(product.getBenefit().getMileage())
            .ageLimit(product.getAgeLimit())
            .discount(product.getBenefit().getDiscount())
            .buySatisfy(product.getBuySatisfy());
        if(product.getCategory() != null){
            return builder.categoryId(product.getCategory().getId()).build();
        }
        return builder.build();
    }
}
