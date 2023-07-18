package com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {

    @Override
    public int apply(int originalPrice, int discountValue) {
        return originalPrice - (originalPrice * discountValue / 100);
    }
}
