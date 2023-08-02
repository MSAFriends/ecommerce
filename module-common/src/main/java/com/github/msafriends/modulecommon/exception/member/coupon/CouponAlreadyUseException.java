package com.github.msafriends.modulecommon.exception.member.coupon;

import com.github.msafriends.modulecommon.exception.BusinessException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class CouponAlreadyUseException extends BusinessException {

    public CouponAlreadyUseException(Long alreadyUseMemberCouponId, String alreadyUseMemberCouponName) {
        super(ErrorCode.COUPON_ALREADY_USE, alreadyUseMemberCouponId, alreadyUseMemberCouponName);
    }
}
