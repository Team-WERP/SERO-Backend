package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class WorkOrderItemNotFoundException extends BusinessException {

    public WorkOrderItemNotFoundException() {
        super(ErrorCode.WO_ITEM_NOT_FOUND);
    }
}
