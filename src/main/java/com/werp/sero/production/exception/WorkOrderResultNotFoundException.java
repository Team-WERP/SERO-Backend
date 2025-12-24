package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderResultNotFoundException extends BusinessException {
    public WorkOrderResultNotFoundException() {
        super(ErrorCode.WO_RESULT_NOT_FOUND);
    }
}
