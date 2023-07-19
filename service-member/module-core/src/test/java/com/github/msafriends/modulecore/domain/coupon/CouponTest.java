package com.github.msafriends.modulecore.domain.coupon;

import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.fixture.CouponFixture;
import com.github.msafriends.modulecore.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CouponTest {

    public static final String COUPON_NAME = "1000원 할인 쿠폰";

    @Nested
    @DisplayName("쿠폰 등록 테스트")
    class CouponRegisterTests {

        @Test
        @DisplayName("쿠폰 등록이 성공적으로 이루어 진다.")
        void testRegisterCoupon() {
            Member member = MemberFixture.createMember();
            Coupon coupon = Coupon.builder()
                    .name(COUPON_NAME)
                    .value(1000)
                    .discountType(CouponDiscountType.FIXED)
                    .member(member)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();
            assertThat(coupon.getStartAt()).isEqualTo(CouponFixture.createStartAt());
            assertThat(coupon.getEndAt()).isEqualTo(CouponFixture.createEndAt());
            assertThat(coupon.getName()).isEqualTo(COUPON_NAME);
            assertThat(coupon.getValue()).isEqualTo(1000);
            assertThat(coupon.getHasUsed()).isEqualTo(false);
        }

        @Test
        @DisplayName("쿠폰 등록시 value 값이 잘못 들어온 경우 등록이 실패한다.")
        void testRegisterCouponWithInvalidValue() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() ->
                    Coupon.builder()
                            .name(COUPON_NAME)
                            .value(-1000)
                            .discountType(CouponDiscountType.FIXED)
                            .member(member)
                            .startAt(CouponFixture.createStartAt())
                            .endAt(CouponFixture.createEndAt())
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Value must be greater than 0.");

            assertThatThrownBy(() ->
                    Coupon.builder()
                            .name(COUPON_NAME)
                            .value(1000)
                            .discountType(CouponDiscountType.PERCENTAGE)
                            .member(member)
                            .startAt(CouponFixture.createStartAt())
                            .endAt(CouponFixture.createEndAt())
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Percentage value cannot exceed 100.");
        }

        @Test
        @DisplayName("쿠폰 등록시 endAt이 startAt 보다 빠른 경우 등록이 실패한다.")
        void testRegisterCouponWithInvalidExpiration() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() ->
                    Coupon.builder()
                            .name(COUPON_NAME)
                            .value(1000)
                            .discountType(CouponDiscountType.FIXED)
                            .member(member)
                            .startAt(CouponFixture.createEndAt())
                            .endAt(CouponFixture.createStartAt())
                            .build())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The coupon's expiration date is incorrectly set.");
        }
    }

    @Nested
    @DisplayName("쿠폰 사용 테스트")
    class CouponUseTests {

        @Test
        @DisplayName("쿠폰 사용이 성공적으로 이루어 진다.")
        void testUseCoupon() {
            Member member = MemberFixture.createMember();
            Coupon coupon = Coupon.builder()
                    .name(COUPON_NAME)
                    .value(1000)
                    .discountType(CouponDiscountType.FIXED)
                    .member(member)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();

            coupon.use(LocalDateTime.now());
            assertThat(coupon.getHasUsed()).isEqualTo(true);
        }

        @Test
        @DisplayName("이미 사용한 쿠폰을 사용할 경우 이미 사용했다는 값을 전달한다.")
        void testUseCouponWithUsedCoupon() {
            Member member = MemberFixture.createMember();
            Coupon coupon = Coupon.builder()
                    .name(COUPON_NAME)
                    .value(1000)
                    .discountType(CouponDiscountType.FIXED)
                    .member(member)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();

            coupon.use(LocalDateTime.now());
            coupon.use(LocalDateTime.now());
            assertThat(coupon.getHasUsed()).isEqualTo(true);
        }

        @Test
        @DisplayName("만료된 쿠폰을 사용할 경우 사용이 실패한다.")
        void testUseCouponWithInvalidTime() {
            Member member = MemberFixture.createMember();

            Coupon coupon = Coupon.builder()
                    .name(COUPON_NAME)
                    .value(1000)
                    .discountType(CouponDiscountType.FIXED)
                    .member(member)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();

            assertThatThrownBy(() ->
                    coupon.use(CouponFixture.createEndAt().plusDays(1)))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The coupon is not within its validity period.");
        }
    }
}