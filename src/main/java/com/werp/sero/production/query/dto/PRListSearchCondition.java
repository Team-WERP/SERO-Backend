package com.werp.sero.production.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "PRListSearchCondition")
public class PRListSearchCondition {

    @Schema(description = "생산요청일", example = "2025-01-01")
    private String requestedDate;

    @Schema(description = "생산마감일", example = "2025-01-20")
    private String dueDate;

    @Schema(description = "담당자 ID", example = "5")
    private Integer managerId;

    @Schema(description = "생산요청 상태", example = "PR_REQ")
    private String status;

    @Schema(description = "검색 키워드 (요청번호/주문번호/품목명)", example = "PR-2025")
    private String keyword;
}