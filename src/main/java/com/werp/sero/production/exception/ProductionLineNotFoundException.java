package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionLineNotFoundException extends BusinessException {
    public ProductionLineNotFoundException() {
        super(ErrorCode.PR_LINE_NOT_FOUND);
    }
}
