package com.github.msafriends.modulecommon.exception.member.coupon;

import com.github.msafriends.modulecommon.exception.BusinessException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class CouponExceedLimitedQuantityException extends BusinessException {

    public CouponExceedLimitedQuantityException(int maxQuantityPerMember) {
        super(ErrorCode.COUPON_EXCEED_LIMITED_QUANTITY, maxQuantityPerMember);
    }
}
