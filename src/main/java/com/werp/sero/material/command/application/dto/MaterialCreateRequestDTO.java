package com.werp.sero.material.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 자재 등록 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialCreateRequestDTO {

    @Schema(description = "자재명", example = "브레이크 패드 어셈블리")
    private String name;

    @Schema(description = "자재 코드", example = "FG-BRK-PAD")
    private String materialCode;

    @Schema(description = "자재 사양", example = "Front, 280mm 디스크 대응")
    private String spec;

    @Schema(description = "작업 단위", example = "BOX")
    private String operationUnit;

    @Schema(description = "기본 단위", example = "EA")
    private String baseUnit;

    @Schema(description = "최소 주문 수량(MOQ)", example = "500")
    private Integer moq;

    @Schema(description = "사이클 타임(초)", example = "120")
    private Integer cycleTime;

    @Schema(description = "단가", example = "45000")
    private Long unitPrice;

    @Schema(description = "이미지 URL", example = "https://image.url/sample.png")
    private String imageUrl;

    @Schema(description = "단위 변환 비율", example = "1")
    private Integer conversionRate;

    @Schema(description = "안전 재고 수량", example = "200")
    private Integer safetyStock;

    @Schema(
            description = "자재 유형",
            example = "MAT_FG",
            allowableValues = {"MAT_FG", "MAT_RM"}
    )
    private String type;

    @Schema(
            description = "자재 상태",
            example = "MAT_NORMAL",
            allowableValues = {"MAT_NORMAL", "MAT_INACTIVE"}
    )
    private String status;

    @Schema(description = "BOM 구성 목록")
    private List<BomRequest> bomList;

    // =============================
    // BOM 요청 DTO
    // =============================
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "BOM 구성 자재 요청")
    public static class BomRequest {

        @Schema(description = "원자재 ID", example = "1")
        private int rawMaterialId;

        @Schema(description = "소요 수량", example = "2")
        private int requirement;

        @Schema(description = "비고", example = "마찰재")
        private String note;
    }
}
