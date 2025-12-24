package com.werp.sero.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalFilterRequestDTO {
    @Schema(description = "결재 또는 결재선 상태")
    private String status;

    @Schema(description = "검색 키워드 (제목, 결재 코드, 문서 코드, 기안자명 등)")
    private String keyword;

    @Schema(description = "조회 시작일")
    private LocalDate startDate;

    @Schema(description = "조회 종료일")
    private LocalDate endDate;

    @Schema(description = "문서 유형 ex) SO (주문 요청), PR (생산 요청), GI (출고 요청)", defaultValue = "SO")
    private String refType;

    @Schema(description = "열람 여부")
    private Boolean isRead;

    @Schema(description = "수신/참조 구분 (recipient: 수신, referrer: 참조)")
    private String viewType;
}