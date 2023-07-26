package com.github.msafriends.moduleapi.dto.request.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCouponRequest {
    private Long memberId;
    private Long couponId;
    private LocalDateTime startAt;

    public MemberCouponRequest(final Long memberId, final Long couponId, final LocalDateTime startAt) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.startAt = startAt;
    }
}
