package com.werp.sero.shipping.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class DeliveryOrderNotFoundException extends BusinessException {
    public DeliveryOrderNotFoundException() {
        super(ErrorCode.DELIVERY_ORDER_NOT_FOUND);
    }
}
