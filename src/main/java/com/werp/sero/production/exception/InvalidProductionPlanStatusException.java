package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidProductionPlanStatusException extends BusinessException {

    public InvalidProductionPlanStatusException() {
        super(ErrorCode.PP_INVALID_STATUS);
    }

    public InvalidProductionPlanStatusException(String message) {
        super(ErrorCode.PP_INVALID_STATUS, message);
    }
}
