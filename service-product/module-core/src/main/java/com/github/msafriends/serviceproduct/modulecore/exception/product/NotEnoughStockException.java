package com.github.msafriends.serviceproduct.modulecore.exception.product;

import com.github.msafriends.serviceproduct.modulecore.exception.BusinessException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class NotEnoughStockException extends BusinessException {

    public NotEnoughStockException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotEnoughStockException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}
