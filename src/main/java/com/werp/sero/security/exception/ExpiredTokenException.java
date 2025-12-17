package com.werp.sero.security.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ExpiredTokenException extends BusinessException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }

    public ExpiredTokenException(final String message) {
        super(ErrorCode.EXPIRED_TOKEN, message);
    }
}