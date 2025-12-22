package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidPRItemStatusException extends BusinessException {

    public InvalidPRItemStatusException() {
        super(ErrorCode.PR_ITEM_INVALID_STATUS);
    }

    public InvalidPRItemStatusException(String message) {
        super(ErrorCode.PR_ITEM_INVALID_STATUS, message);
    }
}
