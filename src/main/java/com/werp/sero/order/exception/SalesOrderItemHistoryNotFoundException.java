package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class SalesOrderItemHistoryNotFoundException extends BusinessException {

    public SalesOrderItemHistoryNotFoundException() {
        super(ErrorCode.SALES_ORDER_ITEM_HISTORY_NOT_FOUND);
    }
}
