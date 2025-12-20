package com.werp.sero.production.exception;
import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class ProductionItemNotInSalesOrderException extends BusinessException {
    public ProductionItemNotInSalesOrderException() {
        super(ErrorCode.PR_ITEM_NOT_IN_SALES_ORDER);
    }
}