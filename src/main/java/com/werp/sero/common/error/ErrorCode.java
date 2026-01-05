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
    INVALID_MATERIAL_STATUS(HttpStatus.BAD_REQUEST, "MATERIAL004", "잘못된 자재 상태입니다. (허용: MAT_NORMAL, MAT_STOP_PREP, MAT_STOP, MAT_DISCONTINUED)"),
    MATERIAL_ALREADY_ACTIVATED(HttpStatus.CONFLICT, "MATERIAL005", "이미 활성화된 자재입니다."),
    MATERIAL_ALREADY_DEACTIVATED(HttpStatus.CONFLICT, "MATERIAL006", "이미 비활성화된 자재입니다."),
    BOM_NOT_ALLOWED_FOR_NON_FG(HttpStatus.BAD_REQUEST, "MATERIAL007", "BOM은 완제품(MAT_FG)에만 등록할 수 있습니다."),

    /* System */
    SYSTEM_COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "SYSTEM001", "공통코드를 찾을 수 없습니다."),

    /* COMMON CODE */
    COMMON_CODE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "CODE001", "공통코드 타입을 찾을 수 없습니다."),
    COMMON_CODE_TYPE_ALREADY_EXISTS(HttpStatus.CONFLICT, "CODE002", "이미 존재하는 공통코드 타입입니다."),
    COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "CODE003", "공통코드를 찾을 수 없습니다."),
    COMMON_CODE_ALREADY_EXISTS(HttpStatus.CONFLICT, "CODE004", "이미 존재하는 공통코드입니다."),
    COMMON_CODE_IN_USE(HttpStatus.CONFLICT, "CODE005", "사용 중인 공통코드는 삭제할 수 없습니다."),
    COMMON_CODE_TYPE_HAS_CODES(HttpStatus.CONFLICT, "CODE006", "하위 공통코드가 존재하는 타입은 삭제할 수 없습니다."),

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
    INVALID_SALES_ORDER_ID(HttpStatus.BAD_REQUEST, "ORDER004", "해당 고객사의 주문이 아닙니다."),
    SALES_ORDER_MONTHLY_GOAL_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER005", "이달의 주문을 찾을 수 없습니다."),

    /* EMPLOYEE */
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE001", "직원 정보를 찾을 수 없습니다."),
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE002", "부서 정보를 찾을 수 없습니다."),

    /* SALES ORDER ITEM */
    SALES_ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER004", "주문 품목 정보를 찾을 수 없습니다."),
    SALES_ORDER_ITEM_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER005", "주문 품목 수량 변동 이력 정보를 찾을 수 없습니다."),

    /* PRODUCTION */
    PR_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION001", "임시 저장된 생산요청을 찾을 수 없습니다."),
    PR_NOT_DRAFT(HttpStatus.BAD_REQUEST, "PRODUCTION002", "임시 저장 상태의 생산요청만 처리할 수 있습니다."),
    PR_ITEM_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "PRODUCTION003", "생산요청 수량은 0 이상이어야 합니다."),
    PR_ITEM_NOT_IN_SALES_ORDER(HttpStatus.BAD_REQUEST, "PRODUCTION004", "해당 주문에 속하지 않은 품목입니다."),
    PR_REQUEST_EMPTY(HttpStatus.BAD_REQUEST, "PRODUCTION005", "생산요청 수량이 없어 요청할 수 없습니다."),
    PR_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION006", "생산요청을 찾을 수 없습니다."),
    PR_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION007", "생산요청 품목을 찾을 수 없습니다."),
    PR_LINE_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION008", "생산 라인을 찾을 수 없습니다."),
    
    /* GOODS ISSUE */
    GOODS_ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "SHIPPING001", "출고지시 정보를 찾을 수 없습니다."),
    GOODS_ISSUE_ALREADY_EXISTS(HttpStatus.CONFLICT, "SHIPPING003", "해당 납품서로 이미 출고지시가 생성되었습니다."),
    INVALID_GOODS_ISSUE_STATUS(HttpStatus.BAD_REQUEST, "SHIPPING004", "출고 처리할 수 없는 상태입니다. 결재 승인된 출고지시만 처리 가능합니다."),

    /* APPROVAL */
    INVALID_DOCUMENT_TYPE(HttpStatus.BAD_REQUEST, "APPROVAL001", "지원하지 않는 결재 문서 유형입니다."),
    APPROVAL_DUPLICATED(HttpStatus.CONFLICT, "APPROVAL002", "이미 존재하는 결재입니다."),
    APPROVAL_LINE_SEQUENCE_REQUIRED(HttpStatus.BAD_REQUEST, "APPROVAL003", "결재 및 협조는 결재 순서 지정이 필수입니다."),
    APPROVAL_LINE_SEQUENCE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "APPROVAL004", "수신 및 참조는 결재 순서를 지정할 수 없습니다."),
    APPROVAL_LINE_REQUIRED(HttpStatus.BAD_REQUEST, "APPROVAL005", "결재선에는 결재 또는 협조가 1명 이상 포함되어야 합니다."),
    APPROVAL_NOT_FOUND(HttpStatus.NOT_FOUND, "APPROVAL006", "결재 정보를 찾을 수 없습니다."),
    APPROVAL_LINE_SEQUENCE_DUPLICATED(HttpStatus.CONFLICT, "APPROVAL007", "결재 및 협조 결재선의 순서는 중복될 수 없습니다."),
    APPROVAL_LINE_ACCESS_DENIED(HttpStatus.NOT_FOUND, "APPROVAL008", "해당 결재의 결재자가 아닙니다."),
    APPROVAL_ALREADY_PROCESSED(HttpStatus.CONFLICT, "APPROVAL009", "이미 처리가 완료된 결재입니다."),
    APPROVAL_REF_DOCUMENT_ALREADY_PROCESSED(HttpStatus.CONFLICT, "APPROVAL010", "연관된 문서가 이미 승인/반려 처리되었습니다."),
    APPROVAL_NOT_CURRENT_SEQUENCE(HttpStatus.BAD_REQUEST, "APPROVAL011", "본인의 결재 순서가 아닙니다."),
    APPROVAL_LINE_NOT_FOUND(HttpStatus.NOT_FOUND, "APPROVAL012", "결재자 정보를 찾을 수 없습니다."),
    APPROVAL_NOT_SUBMITTED(HttpStatus.BAD_REQUEST, "APPROVAL013", "결재가 상신되지 않은 문서입니다."),
    INVALID_APPROVAL_STATUS(HttpStatus.BAD_REQUEST, "APPROVAL014", "유효하지 않는 결재 상태입니다."),
    INVALID_APPROVER_TYPE(HttpStatus.BAD_REQUEST, "APPROVAL015", "잘못된 결재자 타입입니다. 결재 또는 협조 타입만 허용됩니다."),
    INVALID_PROCESSED_APPROVAL_LINE_STATUS(HttpStatus.BAD_REQUEST, "APPROVAL016", "잘못된 결재선 상태입니다. 승인 또는 반려 상태만 허용됩니다."),
    APPROVAL_TEMPLATE_NAME_DUPLICATED(HttpStatus.CONFLICT, "APPROVAL017", "이미 존재하는 결재선 템플릿 이름입니다."),
    APPROVAL_TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "APPROVAL018", "결재선 템플릿 정보를 찾을 수 없습니다."),

    /* DELIVERY ORDER */
    DELIVERY_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "SHIPPING002", "납품서 정보를 찾을 수 없습니다."),

    /* DELIVERY */
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "DELIVERY001", "배송 정보를 찾을 수 없습니다."),
    UNAUTHORIZED_DELIVERY_UPDATE(HttpStatus.FORBIDDEN, "DELIVERY002", "배송 상태를 변경할 권한이 없습니다."),
    INVALID_DELIVERY_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "DELIVERY003", "잘못된 배송 상태 전환입니다."),

    /* FILE */
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "FILE001", "파일이 존재하지 않습니다."),
    FILE_INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "FILE002", "허용되지 않은 파일 형식입니다."),
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE003", "S3 파일 업로드에 실패했습니다."),
    PDF_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE004", "PDF 생성에 실패했습니다."),
    S3_URL_INVALID(HttpStatus.BAD_REQUEST, "FILE005", "유효하지 않은 S3 URL입니다."),

    /* PRODUCTION PLAN */
    PP_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCTION101", "이미 해당 생산요청 품목에 대한 생산계획이 존재합니다."),
    PP_INVALID_PERIOD(HttpStatus.BAD_REQUEST, "PRODUCTION102", "생산 시작일은 종료일보다 늦을 수 없습니다."),
    PP_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "PRODUCTION103", "생산 수량이 올바르지 않습니다."),
    PP_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "PRODUCTION104", "선택한 기간과 라인 기준 생산 가능 수량을 초과했습니다."),
    PP_LINE_NOT_CAPABLE(HttpStatus.BAD_REQUEST, "PRODUCTION105", "해당 라인에서는 이 품목을 생산할 수 없습니다."),
    PP_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION106", "생산계획 초안(PP_DRAFT)을 찾을 수 없습니다."),
    PP_INVALID_STATUS(HttpStatus.BAD_REQUEST, "PRODUCTION107","현재 상태에서는 해당 생산계획을 처리할 수 없습니다."),
    PR_ITEM_INVALID_STATUS(HttpStatus.BAD_REQUEST, "PRODUCTION009", "현재 상태에서는 해당 생산요청 품목을 처리할 수 없습니다."),
    PP_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION108", "생산계획을 찾을 수 없습니다."),

    /* WORK ORDER */
    WO_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION200", "작업지시를 찾을 수 없습니다."),
    WO_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCTION201", "이미 해당 생산계획에 대한 작업지시가 존재합니다."),
    WO_INVALID_PP_STATUS(HttpStatus.BAD_REQUEST, "PRODUCTION202", "확정되지 않은 생산계획으로는 작업지시를 생성할 수 없습니다."),
    WO_INVALID_PERIOD(HttpStatus.BAD_REQUEST, "PRODUCTION203", "작업지시 생성 기간이 올바르지 않습니다."),
    WO_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "PRODUCTION204", "작업지시 수량이 올바르지 않습니다."),
    WO_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION205", "작업지시 실적을 찾을 수 없습니다."),
    WO_RESULT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCTION206", "이미 작업 실적이 등록된 작업지시입니다."),
    WO_INVALID_WORK_TIME(HttpStatus.BAD_REQUEST, "PRODUCTION207", "작업 시간이 올바르지 않습니다."),
    WO_WORK_TIME_REQUIRED(HttpStatus.BAD_REQUEST, "PRODUCTION208", "작업 시작/종료 시간이 필요합니다."),
    WO_INVALID_STATUS(HttpStatus.BAD_REQUEST, "PRODUCTION209", "작업지시 상태가 올바르지 않습니다."),

    PR_INVALID_MONTH(HttpStatus.BAD_REQUEST, "PRODUCTION010", "월(month) 형식이 올바르지 않습니다. (yyyy-MM)"),
    WO_ITEM_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCTION210", "이미 등록된 작업지시 아이템입니다."),
    WO_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "PRODUCTION211", "작업지시 요청 형식이 올바르지 않습니다."),
    PRODUCTION_LINE_MISMATCH(HttpStatus.BAD_REQUEST, "PRODUCTION212", "잘못된 생산 라인입니다."),
    WO_INVALID_DISTRIBUTED_QUANTITY(HttpStatus.BAD_REQUEST,"PRODUCTION213", "아이템별 생산 수량 합계가 양품 수량과 일치하지 않습니다."),
    WO_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION214", "작업지시 아이템을 찾을 수 없습니다."),
    WO_INVALID_PRODUCED_QUANTITY(HttpStatus.BAD_REQUEST,"PRODUCTION215", "생산 수량은 0 이상이어야 합니다."),
    WO_EXCEED_PLANNED_QUANTITY(HttpStatus.BAD_REQUEST,"PRODUCTION216", "아이템 생산 수량이 계획 수량을 초과할 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}