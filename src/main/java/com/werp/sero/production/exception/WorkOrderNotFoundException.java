package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderNotFoundException extends BusinessException {
    public WorkOrderNotFoundException() {
        super(ErrorCode.WO_NOT_FOUND);
    }
}