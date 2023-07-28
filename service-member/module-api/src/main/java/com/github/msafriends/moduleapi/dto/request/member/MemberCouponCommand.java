package com.github.msafriends.moduleapi.dto.request.member;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import com.github.msafriends.modulecore.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberCouponCommand {
    private Member member;
    private Coupon coupon;
    private LocalDateTime currentTime;

    public MemberCouponCommand(final Member member, final Coupon coupon, final LocalDateTime currentTime) {
        this.member = member;
        this.coupon = coupon;
        this.currentTime = currentTime;
    }

    public MemberCoupon toMemberCoupon() {
        return MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .startAt(currentTime)
                .endAt(currentTime.plusDays(coupon.getValidationRange()))
                .build();
    }
}
