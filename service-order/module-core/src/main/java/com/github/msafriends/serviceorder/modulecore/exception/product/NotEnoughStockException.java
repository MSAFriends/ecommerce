package com.github.msafriends.serviceorder.modulecore.exception.product;

import com.github.msafriends.serviceorder.modulecore.exception.BusinessException;
import com.github.msafriends.serviceorder.modulecore.exception.ErrorCode;

public class NotEnoughStockException extends BusinessException {
    public NotEnoughStockException(Long productId, int stockQuantity){
        super(ErrorCode.INVALID_ORDER_QUANTITY, productId, stockQuantity);
    }
}
