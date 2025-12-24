package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalNotCurrentSequenceException extends BusinessException {
    public ApprovalNotCurrentSequenceException() {
        super(ErrorCode.APPROVAL_NOT_CURRENT_SEQUENCE);
    }
}