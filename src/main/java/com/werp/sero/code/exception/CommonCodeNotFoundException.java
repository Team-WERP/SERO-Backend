package com.werp.sero.code.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class CommonCodeNotFoundException extends BusinessException {
    public CommonCodeNotFoundException() {
        super(ErrorCode.SYSTEM_COMMON_CODE_NOT_FOUND);
    }
}
