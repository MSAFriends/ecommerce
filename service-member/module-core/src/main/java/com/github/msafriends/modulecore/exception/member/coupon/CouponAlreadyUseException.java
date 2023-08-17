package com.github.msafriends.modulecore.exception.member.coupon;

import com.github.msafriends.modulecore.exception.BusinessException;
import com.github.msafriends.modulecore.exception.ErrorCode;

public class CouponAlreadyUseException extends BusinessException {

    public CouponAlreadyUseException(Long alreadyUseMemberCouponId, String alreadyUseMemberCouponName) {
        super(ErrorCode.COUPON_ALREADY_USE, alreadyUseMemberCouponId, alreadyUseMemberCouponName);
    }
}
