package com.werp.sero.code.exception;

import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.ErrorCode;

public class CommonCodeInUseException extends BusinessException {
    public CommonCodeInUseException() {
        super(ErrorCode.COMMON_CODE_IN_USE);
    }
}
