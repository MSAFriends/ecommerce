package com.github.msafriends.modulecommon.exception.member.coupon;

import com.github.msafriends.modulecommon.exception.BusinessException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class CouponOutOfStockException extends BusinessException {

    public CouponOutOfStockException() {
        super(ErrorCode.COUPON_OUT_OF_STOCK);
    }
}
