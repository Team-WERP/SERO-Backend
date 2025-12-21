package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionPlanLineNotCapableException extends BusinessException {
    public ProductionPlanLineNotCapableException() {
        super(ErrorCode.PP_LINE_NOT_CAPABLE);
    }
}