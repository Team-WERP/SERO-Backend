package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionItemQuantityInvalidException extends BusinessException {
    public ProductionItemQuantityInvalidException() {
        super(ErrorCode.PR_ITEM_QUANTITY_INVALID);
    }
}