package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalLineSequenceNotAllowedException extends BusinessException {
    public ApprovalLineSequenceNotAllowedException() {
        super(ErrorCode.APPROVAL_LINE_SEQUENCE_NOT_ALLOWED);
    }
}