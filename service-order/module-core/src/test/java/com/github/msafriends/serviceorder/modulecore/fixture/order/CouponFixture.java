package com.github.msafriends.serviceorder.modulecore.fixture.order;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;

public class CouponFixture {
    public static OrderCoupon createCouponWithFixedDiscount(Order order, int discountAmount) {
        return OrderCoupon.builder()
                .order(order)
                .discountValue(discountAmount)
                .discountType(CouponDiscountType.FIXED)
                .build();
    }

    public static OrderCoupon createCouponWithPercentDiscount(Order order, int discountPercent) {
        return OrderCoupon.builder()
                .order(order)
                .discountValue(discountPercent)
                .discountType(CouponDiscountType.PERCENTAGE)
                .build();
    }
}
