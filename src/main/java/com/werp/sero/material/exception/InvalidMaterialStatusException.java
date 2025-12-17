package com.werp.sero.material.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/**
 * 잘못된 자재 상태일 때 발생하는 예외
 */
public class InvalidMaterialStatusException extends BusinessException {

    public InvalidMaterialStatusException() {
        super(ErrorCode.INVALID_MATERIAL_STATUS);
    }

    public InvalidMaterialStatusException(String message) {
        super(ErrorCode.INVALID_MATERIAL_STATUS, message);
    }
}
