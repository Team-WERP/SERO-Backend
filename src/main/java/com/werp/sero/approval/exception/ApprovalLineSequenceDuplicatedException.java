package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalLineSequenceDuplicatedException extends BusinessException {
    public ApprovalLineSequenceDuplicatedException() {
        super(ErrorCode.APPROVAL_LINE_SEQUENCE_DUPLICATED);
    }
}