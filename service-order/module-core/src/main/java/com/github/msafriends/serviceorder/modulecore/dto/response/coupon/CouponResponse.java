package com.github.msafriends.serviceorder.modulecore.dto.response.coupon;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponResponse {
    private int value;
    private String uuid;
    private CouponDiscountType discountType;

    @Builder
    public CouponResponse(int value, String uuid, CouponDiscountType discountType) {
        this.value = value;
        this.uuid = uuid;
        this.discountType = discountType;
    }
}
