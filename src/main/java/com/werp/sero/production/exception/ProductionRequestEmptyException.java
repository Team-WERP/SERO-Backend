package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionRequestEmptyException extends BusinessException {
    public ProductionRequestEmptyException() {
        super(ErrorCode.PR_REQUEST_EMPTY);
    }
}