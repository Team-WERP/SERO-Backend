package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionPlanAlreadyExistsException extends BusinessException {
    public ProductionPlanAlreadyExistsException() {
        super(ErrorCode.PP_ALREADY_EXISTS);
    }
}

