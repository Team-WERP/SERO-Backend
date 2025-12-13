package com.werp.sero.material.exception;

import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.ErrorCode;


/* 설명. 자재 코드가 중복될 때 발생하는 예외 */
public class MaterialCodeDuplicatedException extends BusinessException {

    public MaterialCodeDuplicatedException() {
        super(ErrorCode.MATERIAL_CODE_DUPLICATED);
    }

    public MaterialCodeDuplicatedException(String message) {
        super(ErrorCode.MATERIAL_CODE_DUPLICATED, message);
    }
}
