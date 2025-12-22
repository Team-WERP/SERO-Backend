package com.werp.sero.shipping.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "납품서 목록 조회 응답 DTO")
public class DOListResponseDTO {

    @Schema(description = "납품서 문서번호", example = "DO-20251219-01")
    private String doCode;

    @Schema(description = "수주 문서번호", example = "SO-20251210-01")
    private String soCode;

    @Schema(description = "고객사명", example = "현대모비스")
    private String companyName;

    @Schema(description = "납품서 생성일자", example = "2025-12-19")
    private String createdAt;

    @Schema(description = "납기일시", example = "2025-12-25 14:00")
    private String shippedAt;

    @Schema(description = "담당자명", example = "김철수")
    private String managerName;

    @Schema(description = "품목명 (첫 번째 품목)", example = "모터코어 A")
    private String itemName;

    @Schema(description = "총 품목 개수", example = "3")
    private int itemCount;

    @Schema(description = "상태", example = "DO_BEFORE_GI")
    private String status;
}
