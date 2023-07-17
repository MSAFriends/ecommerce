package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceResponse {

    private int totalPrice;
    private int discountedPrice;

    @Builder
    public PriceResponse(int totalPrice, int discountedPrice) {
        this.totalPrice = totalPrice;
        this.discountedPrice = discountedPrice;
    }

    public static PriceResponse from(final Order order) {
        return PriceResponse.builder()
                .totalPrice(order.getTotalPrice())
                .discountedPrice(order.getDiscountedPrice())
                .build();
    }
}
