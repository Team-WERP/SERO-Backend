package com.werp.sero.warehouse.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

<<<<<<< HEAD
/**
 * 재고가 부족할 때 발생하는 예외
 */
public class InsufficientStockException extends BusinessException {

=======
public class InsufficientStockException extends BusinessException {
>>>>>>> origin/develop
    public InsufficientStockException() {
        super(ErrorCode.INSUFFICIENT_STOCK);
    }

<<<<<<< HEAD
    public InsufficientStockException(String message) {
        super(ErrorCode.INSUFFICIENT_STOCK, message);
=======
    public InsufficientStockException(String customMessage) {
        super(ErrorCode.INSUFFICIENT_STOCK, customMessage);
>>>>>>> origin/develop
    }
}
