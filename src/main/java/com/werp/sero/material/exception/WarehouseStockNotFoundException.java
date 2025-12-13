package com.werp.sero.material.exception;

import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.ErrorCode;

/**
 * 재고를 찾을 수 없을 때 발생하는 예외
 */
public class WarehouseStockNotFoundException extends BusinessException {

    public WarehouseStockNotFoundException() {
        super(ErrorCode.WAREHOUSE_STOCK_NOT_FOUND);
    }

    public WarehouseStockNotFoundException(String message) {
        super(ErrorCode.WAREHOUSE_STOCK_NOT_FOUND, message);
    }
}
