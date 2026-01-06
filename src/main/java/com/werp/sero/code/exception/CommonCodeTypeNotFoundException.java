package com.werp.sero.code.exception;

import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.ErrorCode;

public class CommonCodeTypeNotFoundException extends BusinessException {
    public CommonCodeTypeNotFoundException() {
        super(ErrorCode.COMMON_CODE_TYPE_NOT_FOUND);
    }
}
