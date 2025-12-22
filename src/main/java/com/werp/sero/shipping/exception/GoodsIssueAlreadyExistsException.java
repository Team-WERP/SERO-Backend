package com.werp.sero.shipping.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class GoodsIssueAlreadyExistsException extends BusinessException {
    public GoodsIssueAlreadyExistsException() {
        super(ErrorCode.GOODS_ISSUE_ALREADY_EXISTS);
    }
}
