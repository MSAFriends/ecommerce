package com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy;

public class FixedDiscountStrategy implements DiscountStrategy {

    @Override
    public int apply(int originalPrice, int discountValue) {
        return originalPrice - discountValue;
    }
}
