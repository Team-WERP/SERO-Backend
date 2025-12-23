package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalLineAccessDeniedException extends BusinessException {
    public ApprovalLineAccessDeniedException() {
        super(ErrorCode.APPROVAL_LINE_ACCESS_DENIED);
    }
}