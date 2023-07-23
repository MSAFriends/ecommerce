package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.dto.response.coupon.OrderCouponResponse;

public class OrderCouponFixture {
    public static final Long DEFAULT_COUPON_ID = 1L;
    public static final String DEFAULT_COUPON_NAME = "테스트 쿠폰";
    public static final int DEFAULT_COUPON_DISCOUNT_VALUE = 1000;
    public static final int DEFAULT_COUPON_DISCOUNT_PERCENT = 10;

    public static OrderCoupon createCouponWithFixedDiscount(Order order, int discountAmount) {
        return OrderCoupon.builder()
                .order(order)
                .couponId(DEFAULT_COUPON_ID)
                .name(DEFAULT_COUPON_NAME)
                .discountValue(discountAmount)
                .discountType(CouponDiscountType.FIXED)
                .build();
    }

    public static OrderCoupon createCouponWithPercentDiscount(Order order, int discountPercent) {
        return OrderCoupon.builder()
                .order(order)
                .couponId(DEFAULT_COUPON_ID)
                .name(DEFAULT_COUPON_NAME)
                .discountValue(discountPercent)
                .discountType(CouponDiscountType.PERCENTAGE)
                .build();
    }

    private static OrderCouponResponse.OrderCouponResponseBuilder createDefaultOrderCouponResponseWithFixedDiscount() {
        return OrderCouponResponse.builder()
                .id(DEFAULT_COUPON_ID)
                .name(DEFAULT_COUPON_NAME)
                .discountType(CouponDiscountType.FIXED)
                .value(DEFAULT_COUPON_DISCOUNT_VALUE);
    }

    private static OrderCouponResponse.OrderCouponResponseBuilder createDefaultOrderCouponResponseWithPercentDiscount() {
        return OrderCouponResponse.builder()
                .id(DEFAULT_COUPON_ID)
                .name(DEFAULT_COUPON_NAME)
                .discountType(CouponDiscountType.PERCENTAGE)
                .value(DEFAULT_COUPON_DISCOUNT_PERCENT);
    }

    public static OrderCouponResponse createFixedDiscountOrderCouponResponseWithAmount(int amount) {
        return createDefaultOrderCouponResponseWithFixedDiscount()
                .value(amount)
                .build();
    }

    public static OrderCouponResponse createPercentDiscountOrderCouponResponseWithAmount(int amount) {
        return createDefaultOrderCouponResponseWithPercentDiscount()
                .value(amount)
                .build();
    }

    public static OrderCouponResponse createFixedDiscountOrderCouponResponseWithId(Long id) {
        return createDefaultOrderCouponResponseWithFixedDiscount()
                .id(id)
                .build();
    }

    public static OrderCouponResponse createPercentDiscountOrderCouponResponseWithId(Long id) {
        return createDefaultOrderCouponResponseWithPercentDiscount()
                .id(id)
                .build();
    }
}
