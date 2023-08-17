package com.github.msafriends.modulecore.exception.member.coupon;

import com.github.msafriends.modulecore.exception.BusinessException;
import com.github.msafriends.modulecore.exception.ErrorCode;

public class CouponOutOfStockException extends BusinessException {

    public CouponOutOfStockException() {
        super(ErrorCode.COUPON_OUT_OF_STOCK);
    }
}
