package com.werp.sero.client.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ClientNotFoundException extends BusinessException {

    public ClientNotFoundException() { super(ErrorCode.CLIENT_NOT_FOUND); }



}
