package com.github.msafriends.moduleapi.dto.request.member;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import com.github.msafriends.modulecore.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCouponCommand {
    private Member member;
    private Coupon coupon;
    private MemberCouponRequest request;

    public MemberCouponCommand(final Member member, final Coupon coupon, final MemberCouponRequest request) {
        this.member = member;
        this.coupon = coupon;
        this.request = request;
    }

    public MemberCoupon toMemberCoupon() {
        return MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .build();
    }
}
