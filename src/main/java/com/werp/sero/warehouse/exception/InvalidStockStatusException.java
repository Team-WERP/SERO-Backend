package com.werp.sero.warehouse.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/**
 * 잘못된 재고 상태일 때 발생하는 예외
 */
public class InvalidStockStatusException extends BusinessException {

    public InvalidStockStatusException() {
        super(ErrorCode.INVALID_STOCK_STATUS);
    }

    public InvalidStockStatusException(String message) {
        super(ErrorCode.INVALID_STOCK_STATUS, message);
    }
}
