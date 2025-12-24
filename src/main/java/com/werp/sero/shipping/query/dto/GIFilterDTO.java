package com.werp.sero.shipping.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "출고지시 필터링 요청 DTO")
public class GIFilterDTO {

    @Schema(description = "창고 ID (null 또는 0이면 전체 조회)", example = "1")
    private Integer warehouseId;

    @Schema(description = "상태 (null이면 전체 조회)",
            example = "GI_RVW",
            allowableValues = {"GI_RVW", "GI_APPR_PEND", "GI_APPR_DONE", "GI_APPR_RJCT", "GI_ISSUED", "GI_SHIP_ING", "GI_SHIP_DONE", "GI_CANCEL"})
    private String status;

    @Schema(description = "납기일 시작일 (shipped_at)", example = "2025-01-01")
    private String startDate;

    @Schema(description = "납기일 종료일 (shipped_at)", example = "2025-12-31")
    private String endDate;

    @Schema(description = "검색어 (출고지시번호)", example = "GI-20251207-001")
    private String searchKeyword;
}
