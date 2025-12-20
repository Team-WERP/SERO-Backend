package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidApprovalTargetTypeException extends BusinessException {
    public InvalidApprovalTargetTypeException() {
        super(ErrorCode.INVALID_APPROVAL_TARGET_TYPE);
    }

    public InvalidApprovalTargetTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}