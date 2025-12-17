package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class SalesOrderNotFoundException extends BusinessException {
    public SalesOrderNotFoundException() { super(ErrorCode.EMPLOYEE_NOT_FOUND); }
}
