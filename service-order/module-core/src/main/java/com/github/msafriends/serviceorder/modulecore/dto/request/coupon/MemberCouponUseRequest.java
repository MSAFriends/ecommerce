package com.github.msafriends.serviceorder.modulecore.dto.request.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCouponUseRequest {
    private List<Long> memberCouponIds;

    public MemberCouponUseRequest(final List<Long> memberCouponIds) {
        this.memberCouponIds = memberCouponIds;
    }
}