package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponResponse {
    private int value;
    private Boolean hasUsed;
    private String uuid;
    private CouponDiscountType discountType;

    @Builder
    public CouponResponse(int value, Boolean hasUsed, String uuid, CouponDiscountType discountType) {
        this.value = value;
        this.hasUsed = hasUsed;
        this.uuid = uuid;
        this.discountType = discountType;
    }

    public int applyDiscount(int price) {
        return Math.max(0, discountType.strategy().apply(price, value));
    }
}
