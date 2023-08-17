package com.github.msafriends.modulecore.exception.member.coupon;

import com.github.msafriends.modulecore.exception.BusinessException;
import com.github.msafriends.modulecore.exception.ErrorCode;

public class CouponExpiredException extends BusinessException {

    public CouponExpiredException() {
        super(ErrorCode.COUPON_EXPIRED_ERROR);
    }
}
