package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalRefDocumentAlreadyProcessedException extends BusinessException {
    public ApprovalRefDocumentAlreadyProcessedException() {
        super(ErrorCode.APPROVAL_REF_DOCUMENT_ALREADY_PROCESSED);
    }
}