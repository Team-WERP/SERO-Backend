package com.werp.sero.warehouse.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

/**
 * 창고를 찾을 수 없을 때 발생하는 예외
 */
public class WarehouseNotFoundException extends BusinessException {

    public WarehouseNotFoundException() {
        super(ErrorCode.WAREHOUSE_NOT_FOUND);
    }

    public WarehouseNotFoundException(String message) {
        super(ErrorCode.WAREHOUSE_NOT_FOUND, message);
    }
}
