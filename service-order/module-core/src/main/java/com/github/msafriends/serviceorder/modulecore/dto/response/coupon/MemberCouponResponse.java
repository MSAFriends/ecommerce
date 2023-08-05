package com.github.msafriends.serviceorder.modulecore.dto.response.coupon;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    public static OrderCoupon from(MemberCouponResponse memberCouponResponse) {
        return OrderCoupon.builder()
                .couponId(memberCouponResponse.getId())
                .name(memberCouponResponse.getName())
                .discountType(memberCouponResponse.getDiscountType())
                .discountValue(memberCouponResponse.getDiscountValue())
                .build();
    }

    public static List<OrderCoupon> from(List<MemberCouponResponse> memberCouponResponses) {
        return memberCouponResponses.stream()
                .map(MemberCouponResponse::from)
                .toList();
    }
}
