package com.github.msafriends.serviceproduct.modulecore.exception.member.coupon;

import java.util.List;

import com.github.msafriends.serviceproduct.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class CouponNotExistException extends EntityNotFoundException {
    public CouponNotExistException(List<Long> couponIds) {
        super(ErrorCode.COUPON_NOT_EXIST, couponIds);
    }
}
