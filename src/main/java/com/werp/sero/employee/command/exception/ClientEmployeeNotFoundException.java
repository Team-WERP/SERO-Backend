package com.werp.sero.employee.command.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.EntityNotFoundException;

public class ClientEmployeeNotFoundException extends EntityNotFoundException {
    public ClientEmployeeNotFoundException() {
        super(ErrorCode.CLIENT_EMPLOYEE_NOT_FOUND);
    }
}