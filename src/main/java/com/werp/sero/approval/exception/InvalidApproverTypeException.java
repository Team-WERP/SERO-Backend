package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidApproverTypeException extends BusinessException {
    public InvalidApproverTypeException() {
        super(ErrorCode.INVALID_APPROVER_TYPE);
    }
}