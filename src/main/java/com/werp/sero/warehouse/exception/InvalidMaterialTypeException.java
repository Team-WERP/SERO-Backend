package com.werp.sero.warehouse.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/**
 * 잘못된 자재 유형일 때 발생하는 예외
 */
public class InvalidMaterialTypeException extends BusinessException {

    public InvalidMaterialTypeException() {
        super(ErrorCode.INVALID_MATERIAL_TYPE);
    }

    public InvalidMaterialTypeException(String message) {
        super(ErrorCode.INVALID_MATERIAL_TYPE, message);
    }
}
