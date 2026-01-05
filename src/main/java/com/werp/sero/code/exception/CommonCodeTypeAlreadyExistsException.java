package com.werp.sero.code.exception;

import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.ErrorCode;

public class CommonCodeTypeAlreadyExistsException extends BusinessException {
    public CommonCodeTypeAlreadyExistsException() {
        super(ErrorCode.COMMON_CODE_TYPE_ALREADY_EXISTS);
    }
}
