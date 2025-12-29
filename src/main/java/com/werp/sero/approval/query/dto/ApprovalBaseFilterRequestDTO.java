package com.werp.sero.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalBaseFilterRequestDTO {
    @Schema(description = "검색 키워드 (제목, 결재 코드, 문서 코드, 기안자명 등)", defaultValue = "결재 상신")
    private String keyword;

    @Schema(description = "조회 시작일", defaultValue = "2025-12-01")
    private LocalDate startDate;

    @Schema(description = "조회 종료일", defaultValue = "2025-12-31")
    private LocalDate endDate;

    @Schema(description = "문서 유형 ex) SO (주문 요청), PR (생산 요청), GI (출고 요청)", defaultValue = "SO")
    private String refDocType;
}