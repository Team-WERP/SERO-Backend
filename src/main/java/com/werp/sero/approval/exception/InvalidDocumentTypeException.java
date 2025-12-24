package com.werp.sero.approval.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidDocumentTypeException extends BusinessException {
    public InvalidDocumentTypeException() {
        super(ErrorCode.INVALID_DOCUMENT_TYPE);
    }

    public InvalidDocumentTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}