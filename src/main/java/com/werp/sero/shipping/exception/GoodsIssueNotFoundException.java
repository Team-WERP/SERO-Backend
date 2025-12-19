package com.werp.sero.shipping.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class GoodsIssueNotFoundException extends BusinessException {
    public GoodsIssueNotFoundException() {
        super(ErrorCode.GOODS_ISSUE_NOT_FOUND);
    }
}
