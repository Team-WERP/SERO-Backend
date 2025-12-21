package com.werp.sero.warehouse.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/**
 * 재고가 부족할 때 발생하는 예외
 */
public class InsufficientStockException extends BusinessException {

    public InsufficientStockException() {
        super(ErrorCode.INSUFFICIENT_STOCK);
    }

    public InsufficientStockException(String message) {
        super(ErrorCode.INSUFFICIENT_STOCK, message);
    }
}
