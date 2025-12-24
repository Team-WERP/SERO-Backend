package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionPlanNotFoundException extends BusinessException {
    public ProductionPlanNotFoundException() {
        super(ErrorCode.PP_NOT_FOUND);
    }
}