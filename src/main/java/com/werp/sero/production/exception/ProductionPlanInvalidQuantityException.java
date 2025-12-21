package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionPlanInvalidQuantityException extends BusinessException {
    public ProductionPlanInvalidQuantityException() {
        super(ErrorCode.PP_INVALID_QUANTITY);
    }
}