package com.github.msafriends.serviceproduct.modulecore.exception.member.coupon;

import com.github.msafriends.serviceproduct.modulecore.exception.BusinessException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class CouponAlreadyUseException extends BusinessException {

    public CouponAlreadyUseException(Long alreadyUseMemberCouponId, String alreadyUseMemberCouponName) {
        super(ErrorCode.COUPON_ALREADY_USE, alreadyUseMemberCouponId, alreadyUseMemberCouponName);
    }
}
