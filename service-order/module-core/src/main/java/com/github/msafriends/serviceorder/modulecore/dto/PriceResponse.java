package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    public static PriceResponse from(final Order order, final List<CouponResponse> coupons) {
        return PriceResponse.builder()
                .totalPrice(order.getTotalPrice())
                .discountedPrice(order.getDiscountedPrice(coupons))
                .build();
    }
}
