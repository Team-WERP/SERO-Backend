package com.werp.sero.system.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class SystemCommonCodeNotFoundException extends BusinessException {
    public SystemCommonCodeNotFoundException() {
        super(ErrorCode.SYSTEM_COMMON_CODE_NOT_FOUND);
    }
}
