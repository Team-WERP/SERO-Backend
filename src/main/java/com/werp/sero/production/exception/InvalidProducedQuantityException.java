package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidProducedQuantityException extends BusinessException {

    public InvalidProducedQuantityException() {
        super(ErrorCode.WO_INVALID_PRODUCED_QUANTITY);
    }
}
