package com.werp.sero.auth.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class LoginFailedException extends BusinessException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }
}