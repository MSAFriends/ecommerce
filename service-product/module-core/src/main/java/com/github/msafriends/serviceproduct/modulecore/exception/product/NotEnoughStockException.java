package com.github.msafriends.serviceproduct.modulecore.exception.product;

import com.github.msafriends.serviceproduct.modulecore.exception.BusinessException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class NotEnoughStockException extends BusinessException {
    public NotEnoughStockException(Long productId, int stockQuantity){
        super(ErrorCode.INVALID_ORDER_QUANTITY, productId, stockQuantity);
    }
}
