package com.werp.sero.employee.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.EntityNotFoundException;

public class EmployeeNotFoundException extends EntityNotFoundException {
    public EmployeeNotFoundException() {
        super(ErrorCode.EMPLOYEE_NOT_FOUND);
    }

    public EmployeeNotFoundException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}