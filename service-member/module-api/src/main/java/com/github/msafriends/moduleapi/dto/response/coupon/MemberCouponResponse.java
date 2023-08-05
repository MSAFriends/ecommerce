package com.github.msafriends.moduleapi.dto.response.coupon;

import com.github.msafriends.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCouponResponse {
    private Long id;
    private String name;
    private Boolean hasUsed;
    private CouponDiscountType discountType;
    private int discountValue;
    private LocalDateTime usedAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Builder
    private MemberCouponResponse(
            Long id,
            String name,
            Boolean hasUsed,
            CouponDiscountType discountType,
            int discountValue,
            LocalDateTime usedAt,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        this.id = id;
        this.name = name;
        this.hasUsed = hasUsed;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.usedAt = usedAt;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static MemberCouponResponse from(MemberCoupon memberCoupon) {
        return MemberCouponResponse.builder()
                .id(memberCoupon.getId())
                .name(memberCoupon.getCoupon().getName())
                .hasUsed(memberCoupon.getHasUsed())
                .discountType(memberCoupon.getCoupon().getDiscountType())
                .discountValue(memberCoupon.getCoupon().getValue())
                .usedAt(memberCoupon.getUsedAt())
                .startAt(memberCoupon.getStartAt())
                .endAt(memberCoupon.getEndAt())
                .build();
    }
}
