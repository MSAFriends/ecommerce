package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponResponse {

    private int value;
    private Boolean hasUsed;
    private String uuid;
    private CouponDiscountType discountType;

    public CouponResponse(int value, Boolean hasUsed, String uuid, CouponDiscountType discountType) {
        this.value = value;
        this.hasUsed = hasUsed;
        this.uuid = uuid;
        this.discountType = discountType;
    }
}
