package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionNotFoundException extends BusinessException {
    public ProductionNotFoundException() { super(ErrorCode.PR_NOT_FOUND); }
}
