package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ExceedPlannedQuantityException extends BusinessException {

    public ExceedPlannedQuantityException() {
        super(ErrorCode.WO_EXCEED_PLANNED_QUANTITY);
    }
}
