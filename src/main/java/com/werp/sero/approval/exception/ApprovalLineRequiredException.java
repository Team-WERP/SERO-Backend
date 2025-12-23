package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalLineRequiredException extends BusinessException {
    public ApprovalLineRequiredException() {
        super(ErrorCode.APPROVAL_LINE_REQUIRED);
    }
}