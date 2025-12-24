package com.werp.sero.shipping.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class UnauthorizedDeliveryUpdateException extends BusinessException {
    public UnauthorizedDeliveryUpdateException() {
        super(ErrorCode.UNAUTHORIZED_DELIVERY_UPDATE);
    }
}
