package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidMonthFormatException extends BusinessException {

    public InvalidMonthFormatException() {
        super(ErrorCode.PR_INVALID_MONTH);
    }
}
