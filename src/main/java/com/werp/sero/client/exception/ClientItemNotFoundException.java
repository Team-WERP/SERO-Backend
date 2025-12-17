package com.werp.sero.client.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ClientItemNotFoundException extends BusinessException{


    public ClientItemNotFoundException() {
        super(ErrorCode.CLIENT_ITEM_NOT_FOUND);
    }
}
