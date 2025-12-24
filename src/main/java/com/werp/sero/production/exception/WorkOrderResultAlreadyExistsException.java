package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderResultAlreadyExistsException extends BusinessException {
    public WorkOrderResultAlreadyExistsException() {
        super(ErrorCode.WO_RESULT_ALREADY_EXISTS);
    }
}
