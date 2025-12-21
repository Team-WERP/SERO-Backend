package com.werp.sero.production.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "PRListResponse")
public class PRListResponseDTO {

    @Schema(description = "생산요청 ID", example = "1")
    private int prId;

    @Schema(description = "생산요청 번호", example = "PR-20250101-001")
    private String prCode;

    @Schema(description = "주문 번호", example = "SO-20250101-003")
    private String soCode;

    @Schema(description = "대표 품목명", example = "모터코어 A")
    private String mainItemName;

    @Schema(description = "품목 종류 수", example = "3")
    private int itemTypeCount;

    @Schema(description = "총 요청 수량", example = "120")
    private int totalQuantity;

    @Schema(description = "생산요청 일시", example = "2025-01-10 14:30")
    private String requestedAt;

    @Schema(description = "생산 마감 일시", example = "2025-01-15")
    private String dueAt;

    @Schema(description = "요청자 이름", example = "김철수")
    private String drafterName;

    @Schema(description = "담당자 이름", example = "이영희")
    private String managerName;

    @Schema(description = "생산요청 상태", example = "PR_RVW")
    private String status;
}
