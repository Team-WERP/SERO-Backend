package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalLineNotFoundException extends BusinessException {
    public ApprovalLineNotFoundException() {
        super(ErrorCode.APPROVAL_LINE_NOT_FOUND);
    }
}
