package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderInvalidWorkTimeException extends BusinessException {
    public WorkOrderInvalidWorkTimeException() {
        super(ErrorCode.WO_INVALID_WORK_TIME);
    }
}
