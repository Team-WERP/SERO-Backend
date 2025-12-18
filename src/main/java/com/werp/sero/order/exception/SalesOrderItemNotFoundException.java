package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class SalesOrderItemNotFoundException extends BusinessException {

    public SalesOrderItemNotFoundException() {
        super(ErrorCode.SALES_ORDER_ITEM_NOT_FOUND);
    }
}
