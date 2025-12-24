package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SOClientFilterDTO {
    @Schema(description = "날짜 기준 (orderedAt: 주문일, shippedAt: 납기일)", example = "orderedAt")
    private String dateField;

    @Schema(description = "시작일", example = "2025-12-01")
    private String startDate;

    @Schema(description = "종료일", example = "2025-12-31")
    private String endDate;

    @Schema(description = "주문 상태 (전체일 경우 null)", example = "ORD_RED")
    private String status;

    @Schema(description = "검색 키워드 (주문번호 또는 PO번호)", example = "SO-20251220-001")
    private String searchKeyword;
}