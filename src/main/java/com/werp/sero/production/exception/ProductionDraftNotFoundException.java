package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionDraftNotFoundException extends BusinessException {
    public ProductionDraftNotFoundException() { super(ErrorCode.PR_DRAFT_NOT_FOUND); }
}
