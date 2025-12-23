package com.werp.sero.client.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ClientAccessDeniedException extends BusinessException {

    public ClientAccessDeniedException() {
        super(ErrorCode.CLIENT_ACCESS_DENIED);
    }
}
