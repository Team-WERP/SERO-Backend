package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidWorkOrderProductionPlanStatusException extends BusinessException {
    public InvalidWorkOrderProductionPlanStatusException() {
        super(ErrorCode.WO_INVALID_PP_STATUS);
    }
}
