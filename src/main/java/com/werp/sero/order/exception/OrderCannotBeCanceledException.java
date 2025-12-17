package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class OrderCannotBeCanceledException extends BusinessException {
    public OrderCannotBeCanceledException() { super(ErrorCode.ORDER_CANNOT_BE_CANCELED); }
}
