package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class SalesOrderListNotFoundException extends BusinessException {
    public SalesOrderListNotFoundException() {
        super(ErrorCode.SALES_ORDER_LIST_NOT_FOUND);
    }
}
