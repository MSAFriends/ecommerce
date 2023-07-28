package com.github.msafriends.moduleapi.dto.response.coupon;

import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCouponResponse {
    private String name;
    private Boolean hasUsed;
    private LocalDateTime usedAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Builder
    private MemberCouponResponse(
            String name,
            Boolean hasUsed,
            LocalDateTime usedAt,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        this.name = name;
        this.hasUsed = hasUsed;
        this.usedAt = usedAt;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static MemberCouponResponse from(MemberCoupon memberCoupon) {
        return MemberCouponResponse.builder()
                .name(memberCoupon.getCoupon().getName())
                .hasUsed(memberCoupon.getHasUsed())
                .usedAt(memberCoupon.getUsedAt())
                .startAt(memberCoupon.getStartAt())
                .endAt(memberCoupon.getEndAt())
                .build();
    }
}
