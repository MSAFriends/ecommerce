package com.github.msafriends.serviceproduct.modulecore.exception.member.coupon;

import com.github.msafriends.serviceproduct.modulecore.exception.BusinessException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class CouponAlreadyIssuedException extends BusinessException {
    public CouponAlreadyIssuedException(Long couponId, String couponName) {
        super(ErrorCode.COUPON_ALREADY_ISSUED, couponId, couponName);
    }
}
