package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalDuplicatedException extends BusinessException {
    public ApprovalDuplicatedException() {
        super(ErrorCode.APPROVAL_DUPLICATED);
    }

    public ApprovalDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}