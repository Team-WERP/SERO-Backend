package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidSalesOrderIdException extends BusinessException {

    public InvalidSalesOrderIdException() {
        super(ErrorCode.INVALID_SALES_ORDER_ID);
    }
}
