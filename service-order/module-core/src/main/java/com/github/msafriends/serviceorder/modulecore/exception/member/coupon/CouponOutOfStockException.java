package com.github.msafriends.serviceorder.modulecore.exception.member.coupon;

import com.github.msafriends.serviceorder.modulecore.exception.BusinessException;
import com.github.msafriends.serviceorder.modulecore.exception.ErrorCode;

public class CouponOutOfStockException extends BusinessException {

    public CouponOutOfStockException() {
        super(ErrorCode.COUPON_OUT_OF_STOCK);
    }
}
