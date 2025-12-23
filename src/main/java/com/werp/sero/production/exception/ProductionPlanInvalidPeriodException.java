package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionPlanInvalidPeriodException extends BusinessException {
    public ProductionPlanInvalidPeriodException() {
        super(ErrorCode.PP_INVALID_PERIOD);
    }
}