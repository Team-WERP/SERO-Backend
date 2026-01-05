package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class SalesOrderMonthlyGoalNotFoundException extends BusinessException {

    public SalesOrderMonthlyGoalNotFoundException() {
        super(ErrorCode.SALES_ORDER_MONTHLY_GOAL_NOT_FOUND);
    }
}
