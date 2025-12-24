package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalTemplateNotFoundException extends BusinessException {
    public ApprovalTemplateNotFoundException() {
        super(ErrorCode.APPROVAL_TEMPLATE_NOT_FOUND);
    }
}