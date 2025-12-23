package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalAlreadyProcessedException extends BusinessException {
    public ApprovalAlreadyProcessedException() {
        super(ErrorCode.APPROVAL_ALREADY_PROCESSED);
    }
}