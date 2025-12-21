package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionNotDraftException extends BusinessException {
    public ProductionNotDraftException() {
        super(ErrorCode.PR_NOT_DRAFT);
    }
}
