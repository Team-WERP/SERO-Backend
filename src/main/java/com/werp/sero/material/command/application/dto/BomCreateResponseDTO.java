package com.werp.sero.material.command.application.dto;

import com.werp.sero.material.command.domain.aggregate.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BOM 구성 등록/수정 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "BOM 구성 등록/수정 응답")
public class BomCreateResponseDTO {

    @Schema(description = "자재 ID", example = "7")
    private int materialId;

    @Schema(description = "자재명", example = "브레이크 패드 어셈블리")
    private String materialName;

    @Schema(description = "자재 코드", example = "FG-BRK-PAD")
    private String materialCode;

    @Schema(description = "BOM 구성 개수", example = "3")
    private int bomCount;

    @Schema(description = "BOM 구성 목록")
    private List<BomItemResponse> bomList;

    /**
     * BOM 구성 항목 응답
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "BOM 구성 자재 정보")
    public static class BomItemResponse {

        @Schema(description = "BOM ID", example = "1")
        private int bomId;

        @Schema(description = "원자재 ID", example = "2")
        private int rawMaterialId;

        @Schema(description = "원자재명", example = "마찰재")
        private String rawMaterialName;

        @Schema(description = "원자재 코드", example = "RM-001")
        private String rawMaterialCode;

        @Schema(description = "소요 수량", example = "2")
        private int requirement;

        @Schema(description = "비고", example = "마찰재")
        private String note;
    }

    /**
     * Material 엔티티를 BomCreateResponseDTO로 변환
     */
    public static BomCreateResponseDTO from(Material material) {
        List<BomItemResponse> bomItems = material.getBomList().stream()
                .map(bom -> BomItemResponse.builder()
                        .bomId(bom.getId())
                        .rawMaterialId(bom.getRawMaterial().getId())
                        .rawMaterialName(bom.getRawMaterial().getName())
                        .rawMaterialCode(bom.getRawMaterial().getMaterialCode())
                        .requirement(bom.getRequirement())
                        .note(bom.getNote())
                        .build())
                .collect(Collectors.toList());

        return BomCreateResponseDTO.builder()
                .materialId(material.getId())
                .materialName(material.getName())
                .materialCode(material.getMaterialCode())
                .bomCount(bomItems.size())
                .bomList(bomItems)
                .build();
    }
}
