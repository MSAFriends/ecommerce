package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.dto.request.coupon.MemberCouponUseRequest;

import java.util.List;

public class MemberCouponFixture {

    private static final List<Long> MEMBER_COUPON_IDS = List.of(1L, 2L, 3L);

    public static MemberCouponUseRequest createDefaultMemberCouponUseRequest() {
        return createMemberCouponUseRequest(MEMBER_COUPON_IDS);
    }

    public static MemberCouponUseRequest createMemberCouponUseRequest(List<Long> memberCouponIds) {
        return new MemberCouponUseRequest(memberCouponIds);
    }
}
