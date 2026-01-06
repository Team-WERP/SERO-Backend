package com.werp.sero.code.exception;

import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.ErrorCode;

public class CommonCodeAlreadyExistsException extends BusinessException {
    public CommonCodeAlreadyExistsException() {
        super(ErrorCode.COMMON_CODE_ALREADY_EXISTS);
    }
}
