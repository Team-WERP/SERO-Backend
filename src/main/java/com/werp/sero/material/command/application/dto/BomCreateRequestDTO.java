package com.werp.sero.material.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BOM 구성 등록 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "BOM 구성 등록 요청")
public class BomCreateRequestDTO {

    @Schema(description = "BOM 구성 목록")
    private List<BomItemRequest> bomList;

    /**
     * BOM 구성 항목
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "BOM 구성 자재 요청")
    public static class BomItemRequest {

        @Schema(description = "원자재 ID", example = "1")
        private int rawMaterialId;

        @Schema(description = "소요 수량", example = "2")
        private int requirement;

        @Schema(description = "비고", example = "마찰재")
        private String note;
    }
}
