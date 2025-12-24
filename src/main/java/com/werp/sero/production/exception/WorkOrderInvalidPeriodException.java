package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderInvalidPeriodException extends BusinessException {
    public WorkOrderInvalidPeriodException() {
        super(ErrorCode.WO_INVALID_PERIOD);
    }
}

