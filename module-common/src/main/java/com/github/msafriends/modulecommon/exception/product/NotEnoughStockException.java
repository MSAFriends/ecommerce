package com.github.msafriends.modulecommon.exception.product;

import com.github.msafriends.modulecommon.exception.BusinessException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class NotEnoughStockException extends BusinessException {
    public NotEnoughStockException(Long productId, int stockQuantity){
        super(ErrorCode.INVALID_ORDER_QUANTITY, productId, stockQuantity);
    }
}
