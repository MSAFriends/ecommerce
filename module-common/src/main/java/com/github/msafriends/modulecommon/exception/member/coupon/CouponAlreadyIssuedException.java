package com.github.msafriends.modulecommon.exception.member.coupon;

import com.github.msafriends.modulecommon.exception.BusinessException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class CouponAlreadyIssuedException extends BusinessException {
    public CouponAlreadyIssuedException(Long couponId, String couponName) {
        super(ErrorCode.COUPON_ALREADY_ISSUED, couponId, couponName);
    }
}
