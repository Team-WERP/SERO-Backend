package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidWorkOrderStatusException extends BusinessException {

    public InvalidWorkOrderStatusException(String currentStatus, String expectedStatus) {
        super(
                ErrorCode.WO_INVALID_STATUS,
                String.format(
                        "작업지시 상태가 올바르지 않습니다. (현재: %s, 필요: %s)",
                        currentStatus,
                        expectedStatus
                )
        );
    }
}
