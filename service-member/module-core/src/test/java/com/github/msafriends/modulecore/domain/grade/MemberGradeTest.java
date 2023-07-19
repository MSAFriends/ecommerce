package com.github.msafriends.modulecore.domain.grade;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.fixture.GradeFixture;
import com.github.msafriends.modulecore.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberGradeTest {

    @Nested
    @DisplayName("멤버 등급 쿠폰 생성 테스트")
    class MemberGradeCouponTest {
        LocalDateTime startAt = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(1);

        LocalDateTime endAt = LocalDateTime.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(0);

        @Test
        @DisplayName("멤버 등급에 따른 혜택에 따라 쿠폰이 발급된다.")
        void testGenerateCouponWithMemberGrade() {
            Member member = MemberFixture.createMember();
            Grade bronzeGrade = GradeFixture.createBronzeGrade();
            GradeBenefit bronzeGradeBenefit = GradeBenefit.builder()
                    .grade(bronzeGrade)
                    .discountType(CouponDiscountType.FIXED)
                    .value(1000)
                    .name("1000원 할인 쿠폰")
                    .build();
            bronzeGrade.addAllBenefits(List.of(bronzeGradeBenefit));
            MemberGrade memberGrade = MemberGrade.builder()
                    .grade(bronzeGrade)
                    .member(member)
                    .build();
            List<Coupon> coupons = memberGrade.generateGradeBenefitCoupons();
            assertThat(coupons).hasSize(1);
            assertThat(coupons.get(0).getHasUsed()).isEqualTo(false);
            assertThat(coupons.get(0).getValue()).isEqualTo(1000);
            assertThat(coupons.get(0).getName()).isEqualTo("1000원 할인 쿠폰");

            // 멤버 등급 혜택에 따른 쿠폰 발급 유효기간 Validation
            assertThat(coupons.get(0).getStartAt()).isEqualTo(startAt);
            assertThat(coupons.get(0).getEndAt()).isEqualTo(endAt);
        }

        @Test
        @DisplayName("멤버 등급에 따른 혜택에 따라 쿠폰들이 발급된다.")
        void testGenerateCouponsWithMemberGrade() {
            Member member = MemberFixture.createMember();
            Grade bronzeGrade = GradeFixture.createBronzeGrade();
            GradeBenefit bronzeGradeBenefit1 = GradeBenefit.builder()
                    .grade(bronzeGrade)
                    .discountType(CouponDiscountType.FIXED)
                    .value(1000)
                    .name("1000원 할인 쿠폰")
                    .build();
            GradeBenefit bronzeGradeBenefit2 = GradeBenefit.builder()
                    .grade(bronzeGrade)
                    .discountType(CouponDiscountType.FIXED)
                    .value(2000)
                    .name("2000원 할인 쿠폰")
                    .build();
            bronzeGrade.addAllBenefits(List.of(bronzeGradeBenefit1, bronzeGradeBenefit2));
            MemberGrade memberGrade = MemberGrade.builder()
                    .grade(bronzeGrade)
                    .member(member)
                    .build();
            List<Coupon> coupons = memberGrade.generateGradeBenefitCoupons();
            assertThat(coupons).hasSize(2);

            // 멤버 등급 혜택에 따른 쿠폰 발급 유효기간 Validation
            assertThat(coupons.get(0).getStartAt()).isEqualTo(coupons.get(1).getStartAt());
            assertThat(coupons.get(0).getEndAt()).isEqualTo(coupons.get(1).getEndAt());
        }
    }
}