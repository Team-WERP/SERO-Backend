package com.werp.sero.material.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/**
 * BOM을 완제품이 아닌 자재에 등록하려고 할 때 발생하는 예외
 */
public class BomNotAllowedForNonFinishedGoodsException extends BusinessException {

    public BomNotAllowedForNonFinishedGoodsException() {
        super(ErrorCode.BOM_NOT_ALLOWED_FOR_NON_FG);
    }

    public BomNotAllowedForNonFinishedGoodsException(String message) {
        super(ErrorCode.BOM_NOT_ALLOWED_FOR_NON_FG, message);
    }
}
