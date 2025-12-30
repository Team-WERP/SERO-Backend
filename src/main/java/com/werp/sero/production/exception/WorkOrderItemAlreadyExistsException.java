package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderItemAlreadyExistsException extends BusinessException {
    public WorkOrderItemAlreadyExistsException() {
        super(ErrorCode.WO_ITEM_ALREADY_EXISTS);
    }
}
