package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderAlreadyExistsException extends BusinessException {
    public WorkOrderAlreadyExistsException() {
        super(ErrorCode.WO_ALREADY_EXISTS);
    }
}
