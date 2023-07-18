package com.github.msafriends.serviceorder.modulecore.fixture.order;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.serviceorder.modulecore.dto.CouponResponse;

public class CouponFixture {
    private static final Long DEFAULT_COUPON_ID = 1L;
    private static final int DEFAULT_DISCOUNT_AMOUNT = 1000;
    private static final int DEFAULT_DISCOUNT_PERCENT = 10;
    private static final String DEFAULT_COUPON_UUID = "1234123412341234";

    public static CouponResponse createCouponWithFixedDiscount(int discountAmount) {
        return CouponResponse.builder()
                .value(DEFAULT_DISCOUNT_AMOUNT)
                .hasUsed(false)
                .uuid(DEFAULT_COUPON_UUID)
                .discountType(CouponDiscountType.FIXED)
                .value(discountAmount)
                .build();
    }

    public static CouponResponse createCouponWithPercentDiscount(int discountPercent) {
        return CouponResponse.builder()
                .value(DEFAULT_DISCOUNT_PERCENT)
                .hasUsed(false)
                .uuid(DEFAULT_COUPON_UUID)
                .discountType(CouponDiscountType.PERCENTAGE)
                .value(discountPercent)
                .build();
    }
}
