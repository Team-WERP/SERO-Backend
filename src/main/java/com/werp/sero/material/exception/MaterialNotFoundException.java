package com.werp.sero.material.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;


/* 설명. 자재를 찾을 수 없을 때 발생하는 예외 */
public class MaterialNotFoundException extends BusinessException {

    public MaterialNotFoundException() {
        super(ErrorCode.MATERIAL_NOT_FOUND);
    }

    public MaterialNotFoundException(String message) {
        super(ErrorCode.MATERIAL_NOT_FOUND, message);
    }
}
