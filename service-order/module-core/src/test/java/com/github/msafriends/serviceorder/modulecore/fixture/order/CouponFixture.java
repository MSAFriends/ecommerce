package com.github.msafriends.serviceorder.modulecore.fixture.order;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.Coupon;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;

public class CouponFixture {
    public static final int DEFAULT_DISCOUNT_AMOUNT = 1000;

    public static Coupon createCouponWithFixedDiscount(Order order, int discountAmount) {
        return Coupon.builder()
                .order(order)
                .discountValue(discountAmount)
                .discountType(CouponDiscountType.FIXED)
                .build();
    }

    public static Coupon createCouponWithPercentDiscount(Order order, int discountPercent) {
        return Coupon.builder()
                .order(order)
                .discountValue(discountPercent)
                .discountType(CouponDiscountType.PERCENTAGE)
                .build();
    }
}
