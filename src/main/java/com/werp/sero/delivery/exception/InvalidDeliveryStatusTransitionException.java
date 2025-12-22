package com.werp.sero.delivery.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class InvalidDeliveryStatusTransitionException extends BusinessException {

    public InvalidDeliveryStatusTransitionException() { super(ErrorCode.INVALID_DELIVERY_STATUS_TRANSITION);}
}
