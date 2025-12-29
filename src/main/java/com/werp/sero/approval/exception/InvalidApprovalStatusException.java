package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidApprovalStatusException extends BusinessException {
    public InvalidApprovalStatusException() {
        super(ErrorCode.INVALID_APPROVAL_STATUS);
    }
}