package com.github.msafriends.modulecore.exception.member.coupon;

import java.util.List;

import com.github.msafriends.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.modulecore.exception.ErrorCode;

public class CouponNotExistException extends EntityNotFoundException {
    public CouponNotExistException(List<Long> couponIds) {
        super(ErrorCode.COUPON_NOT_EXIST, couponIds);
    }
}
