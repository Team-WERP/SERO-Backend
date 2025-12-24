package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderWorkTimeRequiredException extends BusinessException {
    public WorkOrderWorkTimeRequiredException() {
        super(ErrorCode.WO_WORK_TIME_REQUIRED);
    }
}
