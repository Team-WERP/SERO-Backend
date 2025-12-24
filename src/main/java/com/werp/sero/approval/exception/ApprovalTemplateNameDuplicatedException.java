package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ApprovalTemplateNameDuplicatedException extends BusinessException {
    public ApprovalTemplateNameDuplicatedException() {
        super(ErrorCode.APPROVAL_TEMPLATE_NAME_DUPLICATED);
    }
}