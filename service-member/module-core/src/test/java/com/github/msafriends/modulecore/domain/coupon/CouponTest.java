package com.github.msafriends.modulecore.domain.coupon;

import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CouponTest {

    public static final String COUPON_NAME = "1000원 할인 쿠폰";

    @Nested
    @DisplayName("쿠폰 등록 테스트")
    class CouponRegisterTests {

        @Test
        @DisplayName("쿠폰 등록이 성공적으로 이루어 진다.")
        void testRegisterCoupon() {
            Coupon limitedCoupon = Coupon.ByLimitedBuilder()
                    .name(COUPON_NAME)
                    .value(1000)
                    .discountType(CouponDiscountType.FIXED)
                    .maxQuantityPerMember(1)
                    .generateType(CouponGenerateType.BRONZE_GRADE_BENEFIT)
                    .quantity(100)
                    .validationRange(30)
                    .build();

            assertThat(limitedCoupon.getName()).isEqualTo(COUPON_NAME);
            assertThat(limitedCoupon.getValue()).isEqualTo(1000);
            assertThat(limitedCoupon.getQuantity()).isEqualTo(100);

            Coupon unLimitedCoupon = Coupon.ByUnLimitedBuilder()
                    .name(COUPON_NAME)
                    .value(10)
                    .discountType(CouponDiscountType.PERCENTAGE)
                    .maxQuantityPerMember(1)
                    .generateType(CouponGenerateType.BRONZE_GRADE_BENEFIT)
                    .build();

            assertThat(unLimitedCoupon.getName()).isEqualTo(COUPON_NAME);
            assertThat(unLimitedCoupon.getValue()).isEqualTo(10);
            assertThat(unLimitedCoupon.getQuantity()).isEqualTo(1);
        }

        @Test
        @DisplayName("쿠폰 등록시 value 값이 잘못 들어온 경우 등록이 실패한다.")
        void testRegisterCouponWithInvalidValue() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() ->
                    Coupon.ByUnLimitedBuilder()
                            .name(COUPON_NAME)
                            .value(-1000)
                            .discountType(CouponDiscountType.FIXED)
                            .validationRange(30)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Value must be greater than 0.");

            assertThatThrownBy(() ->
                    Coupon.ByUnLimitedBuilder()
                            .name(COUPON_NAME)
                            .value(1000)
                            .discountType(CouponDiscountType.PERCENTAGE)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Percentage value cannot exceed 100.");
        }
    }
}