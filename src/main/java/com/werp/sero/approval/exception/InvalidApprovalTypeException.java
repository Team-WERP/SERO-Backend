package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidApprovalTypeException extends BusinessException {
    public InvalidApprovalTypeException() {
        super(ErrorCode.INVALID_APPROVAL_TYPE);
    }

    public InvalidApprovalTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}