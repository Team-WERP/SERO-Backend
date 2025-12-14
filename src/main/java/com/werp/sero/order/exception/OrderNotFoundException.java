package com.werp.sero.order.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class OrderNotFoundException extends BusinessException {
    public OrderNotFoundException() { super(ErrorCode.ORDER_NOT_FOUND); }

    public OrderNotFoundException(String message) { super(ErrorCode.ORDER_NOT_FOUND, message); }
}
