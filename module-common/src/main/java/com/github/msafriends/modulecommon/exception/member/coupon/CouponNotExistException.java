package com.github.msafriends.modulecommon.exception.member.coupon;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

import java.util.List;

public class CouponNotExistException extends EntityNotFoundException {
    public CouponNotExistException(List<Long> couponIds) {
        super(ErrorCode.COUPON_NOT_EXIST, couponIds);
    }
}
