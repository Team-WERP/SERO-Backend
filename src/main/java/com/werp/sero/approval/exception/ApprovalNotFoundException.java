package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalNotFoundException extends BusinessException {
    public ApprovalNotFoundException() {
        super(ErrorCode.APPROVAL_NOT_FOUND);
    }
}