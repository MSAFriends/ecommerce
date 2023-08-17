package com.github.msafriends.serviceproduct.modulecore.exception.member.coupon;

import com.github.msafriends.serviceproduct.modulecore.exception.BusinessException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class CouponExceedLimitedQuantityException extends BusinessException {

    public CouponExceedLimitedQuantityException(int maxQuantityPerMember) {
        super(ErrorCode.COUPON_EXCEED_LIMITED_QUANTITY, maxQuantityPerMember);
    }
}
