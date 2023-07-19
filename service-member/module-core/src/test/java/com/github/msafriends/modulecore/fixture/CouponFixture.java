package com.github.msafriends.modulecore.fixture;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.modulecore.domain.member.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CouponFixture {
    private static final String COUPON_NAME = "10000원 할인 쿠폰";
    private static final int COUPON_VALUE = 1000;

    public static Coupon createCoupon (Member member) {
        return Coupon.builder()
                .member(member)
                .name(COUPON_NAME)
                .value(COUPON_VALUE)
                .discountType(CouponDiscountType.FIXED)
                .startAt(createStartAt())
                .endAt(createEndAt())
                .build();
    }

    public static LocalDateTime createStartAt() {
        return LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(1);
    }
    public static LocalDateTime createEndAt() {
        return LocalDateTime.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(0);
    }
}
