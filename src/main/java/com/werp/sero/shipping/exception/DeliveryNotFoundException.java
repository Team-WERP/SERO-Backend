package com.werp.sero.shipping.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class DeliveryNotFoundException extends BusinessException {
    public DeliveryNotFoundException() {
        super(ErrorCode.DELIVERY_NOT_FOUND);
    }
}
