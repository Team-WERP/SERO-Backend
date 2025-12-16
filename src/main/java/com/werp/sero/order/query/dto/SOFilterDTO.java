package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 필터링 요청 DTO")
public class SOFilterDTO {

    @Schema(description = "주문 상태 그룹코드", example = "ORD_PEND")
    private String statusType;

    @Schema(description = "주문일 또는 납기일(ordered_at / shipped_at)", example = "ordered_at")
    private String dateField;

    @Schema(description = "날짜 범위 시작일", example = "2025-01-01")
    private String startDate;

    @Schema(description = "날짜 범위 종료일", example = "2025-12-31")
    private String endDate;

    @Schema(description = "담당자 id", example = "1")
    private Integer managerId;

    @Schema(description = "고객사 id", example = "1")
    private Integer clientId;

    @Schema(description = "브레이크")
    private String searchKeyword;
}