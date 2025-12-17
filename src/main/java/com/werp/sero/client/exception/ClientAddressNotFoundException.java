package com.werp.sero.client.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ClientAddressNotFoundException extends BusinessException {

    public ClientAddressNotFoundException() {
        super(ErrorCode.CLIENT_ADDRESS_NOT_FOUND);
    }
}
