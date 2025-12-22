package com.werp.sero.warehouse.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InsufficientStockException extends BusinessException {
    public InsufficientStockException() {
        super(ErrorCode.INSUFFICIENT_STOCK);
    }

    public InsufficientStockException(String customMessage) {
        super(ErrorCode.INSUFFICIENT_STOCK, customMessage);
    }
}
