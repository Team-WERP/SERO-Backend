package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalLineSequenceRequiredException extends BusinessException {
    public ApprovalLineSequenceRequiredException() {
        super(ErrorCode.APPROVAL_LINE_SEQUENCE_REQUIRED);
    }
}