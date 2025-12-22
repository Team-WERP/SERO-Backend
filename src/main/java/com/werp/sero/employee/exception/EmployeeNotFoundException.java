package com.werp.sero.employee.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class EmployeeNotFoundException extends BusinessException {
    public EmployeeNotFoundException() {
        super(ErrorCode.EMPLOYEE_NOT_FOUND);
    }

    public EmployeeNotFoundException(String message) {
        super(ErrorCode.EMPLOYEE_NOT_FOUND, message);
    }
}