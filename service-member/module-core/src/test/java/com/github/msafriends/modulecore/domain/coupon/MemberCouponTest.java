package com.github.msafriends.modulecore.domain.coupon;

import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.fixture.CouponFixture;
import com.github.msafriends.modulecore.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberCouponTest {

    @Nested
    @DisplayName("멤버쿠폰 등록 테스트")
    class MemberCouponRegisterTests {

        @Test
        @DisplayName("멤버쿠폰 등록이 성공적으로 이루어 진다.")
        void testRegisterMemberCoupon() {
            Member member = MemberFixture.createMember();
            Coupon coupon = CouponFixture.createCoupon();

            MemberCoupon memberCoupon = MemberCoupon.builder()
                    .member(member)
                    .coupon(coupon)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();

            assertThat(memberCoupon.getHasUsed()).isEqualTo(false);
            assertThat(memberCoupon.getCoupon().getValue()).isEqualTo(1000);
        }

        @Test
        @DisplayName("멤버쿠폰 등록시 endAt이 startAt 보다 빠른 경우 등록이 실패한다.")
        void testRegisterMemberCouponWithInvalidExpiration() {
            Member member = MemberFixture.createMember();
            Coupon coupon = CouponFixture.createCoupon();

            assertThatThrownBy(() ->
                    MemberCoupon.builder()
                            .member(member)
                            .coupon(coupon)
                            .startAt(CouponFixture.createEndAt())
                            .endAt(CouponFixture.createStartAt())
                            .build())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The coupon's expiration date is incorrectly set.");
        }
    }

    @Nested
    @DisplayName("멤버쿠폰 사용 테스트")
    class MemberCouponUseTests {

        @Test
        @DisplayName("멤버의 쿠폰 사용이 성공적으로 이루어 진다.")
        void testUseMemberCoupon() {
            Member member = MemberFixture.createMember();
            Coupon coupon = CouponFixture.createCoupon();

            MemberCoupon memberCoupon = MemberCoupon.builder()
                    .member(member)
                    .coupon(coupon)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();

            memberCoupon.use(CouponFixture.createExampleDate());
            assertThat(memberCoupon.getHasUsed()).isEqualTo(true);
        }

        @Test
        @DisplayName("이미 사용한 쿠폰을 사용할 경우 이미 사용했다는 값을 전달한다.")
        void testUseCouponWithUsedCoupon() {
            Member member = MemberFixture.createMember();
            Coupon coupon = CouponFixture.createCoupon();
            MemberCoupon memberCoupon = MemberCoupon.builder()
                    .member(member)
                    .coupon(coupon)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();

            memberCoupon.use(CouponFixture.createExampleDate());
            assertThatThrownBy(() ->
                    memberCoupon.use(CouponFixture.createExampleDate()))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The coupon has already been used.");
        }

        @Test
        @DisplayName("만료된 쿠폰을 사용할 경우 사용이 실패한다.")
        void testUseCouponWithInvalidTime() {
            Member member = MemberFixture.createMember();
            Coupon coupon = CouponFixture.createCoupon();
            MemberCoupon memberCoupon = MemberCoupon.builder()
                    .member(member)
                    .coupon(coupon)
                    .startAt(CouponFixture.createStartAt())
                    .endAt(CouponFixture.createEndAt())
                    .build();

            assertThatThrownBy(() ->
                    memberCoupon.use(CouponFixture.createEndAt().plusDays(1)))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The coupon is not within its validity period.");
        }
    }
}
