package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidProcessedApprovalLineStatusException extends BusinessException {
    public InvalidProcessedApprovalLineStatusException() {
        super(ErrorCode.INVALID_PROCESSED_APPROVAL_LINE_STATUS);
    }
}