package com.werp.sero.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "요청한 데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "잘못된 요청입니다."),


    /* MATERIAL */
    MATERIAL_NOT_FOUND(HttpStatus.NOT_FOUND, "MATERIAL001", "자재 정보를 찾을 수 없습니다."),
    MATERIAL_CODE_DUPLICATED(HttpStatus.CONFLICT, "MATERIAL002", "이미 존재하는 자재 코드입니다."),
    INVALID_MATERIAL_TYPE_FOR_MATERIAL(HttpStatus.BAD_REQUEST, "MATERIAL003", "잘못된 자재 유형입니다. (허용: MAT_FG, MAT_RM)"),
    INVALID_MATERIAL_STATUS(HttpStatus.BAD_REQUEST, "MATERIAL004", "잘못된 자재 상태입니다. (허용: MAT_NORMAL, MAT_STOP)"),
    MATERIAL_ALREADY_ACTIVATED(HttpStatus.CONFLICT, "MATERIAL005", "이미 활성화된 자재입니다."),
    MATERIAL_ALREADY_DEACTIVATED(HttpStatus.CONFLICT, "MATERIAL006", "이미 비활성화된 자재입니다."),
    BOM_NOT_ALLOWED_FOR_NON_FG(HttpStatus.BAD_REQUEST, "MATERIAL007", "BOM은 완제품(MAT_FG)에만 등록할 수 있습니다."),

    /* System */
    SYSTEM_COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "SYSTEM001", "공통코드를 찾을 수 없습니다."),


    /* WAREHOUSE */
    WAREHOUSE_STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "WAREHOUSE001", "창고 재고 정보를 찾을 수 없습니다."),
    WAREHOUSE_NOT_FOUND(HttpStatus.NOT_FOUND, "WAREHOUSE002", "창고 정보를 찾을 수 없습니다."),
    INVALID_MATERIAL_TYPE(HttpStatus.BAD_REQUEST, "WAREHOUSE003", "잘못된 자재 유형입니다. (허용: MAT_FG, MAT_RM)"),
    INVALID_STOCK_STATUS(HttpStatus.BAD_REQUEST, "WAREHOUSE004", "잘못된 재고 상태입니다. (허용: NORMAL, LOW, OUT_OF_STOCK)"),

    /* CLIENT */
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT001", "거래처 정보를 찾을 수 없습니다."),
    CLIENT_BUSINESS_NO_DUPLICATED(HttpStatus.CONFLICT, "CLIENT002", "이미 등록된 사업자번호입니다."),
    CLIENT_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT003", "해당 고객사의 거래 품목이 아닙니다."),

    /* SECURITY */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "SECURITY001", "인증되지 않은 사용자입니다."),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "SECURITY002", "해당 기능에 대한 권한이 없습니다."),

    /* EMPLOYEE */
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE001", "직원 정보를 찾을 수 없습니다."),
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE002", "부서 정보를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}