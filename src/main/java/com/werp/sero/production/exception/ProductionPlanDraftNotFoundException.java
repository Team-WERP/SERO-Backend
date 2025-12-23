package com.werp.sero.production.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionPlanDraftNotFoundException extends BusinessException {
    public ProductionPlanDraftNotFoundException() {
        super(ErrorCode.PP_DRAFT_NOT_FOUND);
    }
}