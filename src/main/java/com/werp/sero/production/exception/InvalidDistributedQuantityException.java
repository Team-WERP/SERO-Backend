package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidDistributedQuantityException extends BusinessException {

    public InvalidDistributedQuantityException() {
        super(ErrorCode.WO_INVALID_DISTRIBUTED_QUANTITY);
    }
}
