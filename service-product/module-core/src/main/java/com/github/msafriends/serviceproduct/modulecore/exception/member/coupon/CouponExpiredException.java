package com.github.msafriends.serviceproduct.modulecore.exception.member.coupon;

import com.github.msafriends.serviceproduct.modulecore.exception.BusinessException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class CouponExpiredException extends BusinessException {

    public CouponExpiredException() {
        super(ErrorCode.COUPON_EXPIRED_ERROR);
    }
}
