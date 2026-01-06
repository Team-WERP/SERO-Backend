package com.werp.sero.code.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class CommonCodeTypeHasCodesException extends BusinessException {
    public CommonCodeTypeHasCodesException() {
        super(ErrorCode.COMMON_CODE_TYPE_HAS_CODES);
    }
}
