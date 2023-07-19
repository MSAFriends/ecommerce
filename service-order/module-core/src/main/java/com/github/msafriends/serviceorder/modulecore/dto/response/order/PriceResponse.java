package com.github.msafriends.serviceorder.modulecore.dto.response.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PriceResponse {

    private final int totalPrice;
    private final int discountedPrice;

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
