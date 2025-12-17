package com.werp.sero.material.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/**
 * 이미 비활성화된 자재를 다시 비활성화하려 할 때 발생하는 예외
 */
public class MaterialAlreadyDeactivatedException extends BusinessException {

    public MaterialAlreadyDeactivatedException() {
        super(ErrorCode.MATERIAL_ALREADY_DEACTIVATED);
    }
}
