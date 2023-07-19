package com.github.msafriends.serviceorder.modulecore.domain.coupon;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy.DiscountStrategy;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy.FixedDiscountStrategy;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy.PercentageDiscountStrategy;

public enum CouponDiscountType {
    FIXED(new FixedDiscountStrategy()),
    PERCENTAGE(new PercentageDiscountStrategy());

    private final DiscountStrategy discountStrategy;

    CouponDiscountType(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public DiscountStrategy strategy() {
        return discountStrategy;
    }
}