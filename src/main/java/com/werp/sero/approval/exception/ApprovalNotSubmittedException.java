package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalNotSubmittedException extends BusinessException {
    public ApprovalNotSubmittedException() {
        super(ErrorCode.APPROVAL_NOT_SUBMITTED);
    }
}