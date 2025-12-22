package com.werp.sero.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "요청한 데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "잘못된 요청입니다."),

    /* CLIENT EMPLOYEE */
    CLIENT_EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT_EMPLOYEE001", "고객사 직원 정보를 찾을 수 없습니다."),

    /* AUTH */
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH001", "로그인에 실패했습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH002", "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH003", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH004", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH005", "접근 권한이 없습니다."),

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
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "WAREHOUSE005", "재고가 부족합니다."),

    /* CLIENT */
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT001", "고객사 정보를 찾을 수 없습니다."),
    CLIENT_BUSINESS_NO_DUPLICATED(HttpStatus.CONFLICT, "CLIENT002", "이미 등록된 사업자번호입니다."),
    CLIENT_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT003", "해당 고객사의 거래 품목이 아닙니다."),
    CLIENT_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT004", "배송지를 찾을 수 없습니다."),
    CLIENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CLIENT005", "다른 고객사의 데이터에 접근할 수 없습니다."),

    /* SALES ORDER */
    SALES_ORDER_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER001", "주문 목록을 찾을 수 없습니다."),
    SALES_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER002", "주문 정보를 찾을 수 없습니다."),
    ORDER_CANNOT_BE_CANCELED(HttpStatus.BAD_REQUEST, "ORDER003", "이미 취소 및 완료 되었거나 확정된 주문입니다."),

    /* EMPLOYEE */
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE001", "직원 정보를 찾을 수 없습니다."),
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE002", "부서 정보를 찾을 수 없습니다."),

    /* SALES ORDER ITEM */
    SALES_ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER004", "주문 품목 정보를 찾을 수 없습니다."),

    /* PRODUCTION */
    PR_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION001", "임시 저장된 생산요청을 찾을 수 없습니다."),
    PR_NOT_DRAFT(HttpStatus.BAD_REQUEST, "PRODUCTION002", "임시 저장 상태의 생산요청만 처리할 수 있습니다."),
    PR_ITEM_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "PRODUCTION003", "생산요청 수량은 0 이상이어야 합니다."),
    PR_ITEM_NOT_IN_SALES_ORDER(HttpStatus.BAD_REQUEST, "PRODUCTION004", "해당 주문에 속하지 않은 품목입니다."),
    PR_REQUEST_EMPTY(HttpStatus.BAD_REQUEST, "PRODUCTION005", "생산요청 수량이 없어 요청할 수 없습니다."),
    PR_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION006", "생산요청을 찾을 수 없습니다."),

    /* GOODS ISSUE */
    GOODS_ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "SHIPPING001", "출고지시 정보를 찾을 수 없습니다."),
    GOODS_ISSUE_ALREADY_EXISTS(HttpStatus.CONFLICT, "SHIPPING003", "해당 납품서로 이미 출고지시가 생성되었습니다."),

    /* APPROVAL */
    INVALID_APPROVAL_TYPE(HttpStatus.NOT_FOUND, "APPROVAL001", "지원하지 않는 결재 타입입니다."),
    APPROVAL_DUPLICATED(HttpStatus.CONFLICT, "APPROVAL002", "이미 존재하는 결재입니다."),
    APPROVAL_LINE_SEQUENCE_REQUIRED(HttpStatus.BAD_REQUEST, "APPROVAL003", "결재 및 협조는 결재 순서 지정이 필수입니다."),
    APPROVAL_LINE_SEQUENCE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "APPROVAL004", "수신 및 참조는 결재 순서를 지정할 수 없습니다."),

    /* DELIVERY ORDER */
    DELIVERY_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "SHIPPING002", "납품서 정보를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}