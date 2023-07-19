package com.github.msafriends.modulecore.domain.grade;

import com.github.msafriends.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.modulecore.fixture.GradeFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GradeBenefitTest {

    @Nested
    @DisplayName("등급혜택 등록 테스트")
    class GradeBenefitRegisterTests {

        @Test
        @DisplayName("등급혜택 등록이 성공적으로 업데이트 된다.")
        void testRegisterGradeBenefit() {
            Grade grade = GradeFixture.createBronzeGrade();
            String gradeBenefitNameWithFixedDiscount = "1000원 할인 쿠폰 발급";
            String gradeBenefitNameWithPercentageDiscount = "10% 할인 쿠폰 발급";
            GradeBenefit gradeBenefitWithFixedDiscount = GradeBenefit.builder()
                    .name(gradeBenefitNameWithFixedDiscount)
                    .discountType(CouponDiscountType.FIXED)
                    .grade(grade)
                    .value(1000)
                    .build();

            GradeBenefit gradeBenefitWithPercentageDiscount = GradeBenefit.builder()
                    .name(gradeBenefitNameWithPercentageDiscount)
                    .discountType(CouponDiscountType.PERCENTAGE)
                    .grade(grade)
                    .value(10)
                    .build();

            assertThat(gradeBenefitWithFixedDiscount.getValue()).isEqualTo(1000);
            assertThat(gradeBenefitWithPercentageDiscount.getValue()).isEqualTo(10);
        }

        @Test
        @DisplayName("등급혜택 등록시 할인 금액이 0또는 0미만일 경우 실패한다.")
        void testRegisterGradeBenefitWithValueLessThanZero() {
            Grade grade = GradeFixture.createBronzeGrade();
            String gradeBenefitName = "1000원 할인 쿠폰 발급";

            assertThatThrownBy(() ->
                    GradeBenefit.builder()
                            .name(gradeBenefitName)
                            .discountType(CouponDiscountType.FIXED)
                            .grade(grade)
                            .value(-1000)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Value must be greater than 0.");

            assertThatThrownBy(() ->
                    GradeBenefit.builder()
                            .name(gradeBenefitName)
                            .discountType(CouponDiscountType.FIXED)
                            .grade(grade)
                            .value(0)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Value must be greater than 0.");
        }

        @Test
        @DisplayName("등급혜택 등록시 퍼센티지 할인이 100%가 넘는 경우 실패한다.")
        void testRegisterGradeBenefitWithPercentageGreaterThan100() {
            Grade grade = GradeFixture.createBronzeGrade();
            String gradeBenefitName = "10% 할인 쿠폰 발급";

            assertThatThrownBy(() ->
                    GradeBenefit.builder()
                            .name(gradeBenefitName)
                            .discountType(CouponDiscountType.PERCENTAGE)
                            .grade(grade)
                            .value(101)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Percentage value cannot exceed 100.");
        }

        @Test
        @DisplayName("등급혜택 등록시 이름이 없는 경우 실패한다.")
        void testRegisterGradeBenefitWithBlankName() {
            Grade grade = GradeFixture.createBronzeGrade();
            String gradeBenefitName = "";

            assertThatThrownBy(() ->
                    GradeBenefit.builder()
                            .name(gradeBenefitName)
                            .discountType(CouponDiscountType.PERCENTAGE)
                            .grade(grade)
                            .value(10)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Name must not be empty.");
        }
    }
}