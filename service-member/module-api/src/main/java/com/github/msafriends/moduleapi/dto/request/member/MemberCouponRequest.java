package com.github.msafriends.moduleapi.dto.request.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCouponRequest {
    private Long couponId;

    public MemberCouponRequest(final Long couponId) {
        this.couponId = couponId;
    }
}
