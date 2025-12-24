package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionRequestNotFoundException extends BusinessException {
    public ProductionRequestNotFoundException() { super(ErrorCode.PR_NOT_FOUND); }
}
