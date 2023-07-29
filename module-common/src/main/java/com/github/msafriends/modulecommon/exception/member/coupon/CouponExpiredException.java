package com.github.msafriends.modulecommon.exception.member.coupon;

import com.github.msafriends.modulecommon.exception.BusinessException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class CouponExpiredException extends BusinessException {

    public CouponExpiredException() {
        super(ErrorCode.COUPON_EXPIRED_ERROR);
    }
}
