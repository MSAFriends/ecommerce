package com.github.msafriends.modulecore.fixture;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.modulecore.domain.coupon.CouponGenerateType;
import com.github.msafriends.modulecore.domain.member.Member;

import java.time.LocalDateTime;

public class CouponFixture {
    private static final String COUPON_NAME = "10000원 할인 쿠폰";
    private static final int COUPON_VALUE = 1000;

    public static Coupon createCoupon () {
        return Coupon.ByUnLimitedBuilder()
                .generateType(CouponGenerateType.BRONZE_GRADE_BENEFIT)
                .name(COUPON_NAME)
                .value(COUPON_VALUE)
                .discountType(CouponDiscountType.FIXED)
                .build();
    }

    public static LocalDateTime createStartAt() {
        return LocalDateTime.of(2023, 7, 1, 0, 0, 0, 1);
    }
    public static LocalDateTime createEndAt() {
        return LocalDateTime.of(2023, 7, 31, 23, 59, 59, 0);
    }

    public static LocalDateTime createExampleDate() {
        return LocalDateTime.of(2023, 7, 7, 18, 30, 59);
    }
}
