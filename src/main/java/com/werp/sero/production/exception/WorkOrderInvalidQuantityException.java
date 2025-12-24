package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderInvalidQuantityException extends BusinessException {
    public WorkOrderInvalidQuantityException() {
        super(ErrorCode.WO_INVALID_QUANTITY);
    }
}