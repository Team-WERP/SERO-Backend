package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionPlanCapacityExceededException extends BusinessException {
    public ProductionPlanCapacityExceededException() {
        super(ErrorCode.PP_CAPACITY_EXCEEDED);
    }
}