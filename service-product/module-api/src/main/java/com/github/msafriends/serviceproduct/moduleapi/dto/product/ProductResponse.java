package com.github.msafriends.serviceproduct.moduleapi.dto.product;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Size;

import lombok.Builder;

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
        return ProductResponse.builder()
            .productId(product.getId())
            .name(product.getName())
            .price(product.getPrice().getPriceValue())
            .salesPrice(product.getPrice().getSalePriceValue())
            .quantity(product.getQuantity())
            .code(product.getCode())
            .categoryId(product.getCategory().getId())
            .mileage(product.getBenefit().getMileage())
            .ageLimit(product.getAgeLimit())
            .discount(product.getBenefit().getDiscount())
            .buySatisfy(product.getBuySatisfy())
            .build();
    }
}
