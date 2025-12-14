package com.werp.sero.client.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/* 설명. 고객사를 찾을 수 없을 때 발생하는 예외 */
public class ClientNotFoundException extends BusinessException {

    public ClientNotFoundException() {
        super(ErrorCode.CLIENT_NOT_FOUND);
    }

    public ClientNotFoundException(String message) {
        super(ErrorCode.CLIENT_NOT_FOUND, message);
    }
}
