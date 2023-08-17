package com.github.msafriends.serviceorder.modulecore.exception.member.coupon;

import com.github.msafriends.serviceorder.modulecore.exception.BusinessException;
import com.github.msafriends.serviceorder.modulecore.exception.ErrorCode;

public class CouponExpiredException extends BusinessException {

    public CouponExpiredException() {
        super(ErrorCode.COUPON_EXPIRED_ERROR);
    }
}
