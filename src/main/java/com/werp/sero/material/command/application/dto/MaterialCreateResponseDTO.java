package com.werp.sero.material.command.application.dto;

import com.werp.sero.material.command.domain.aggregate.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자재 등록 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "자재 등록 응답")
public class MaterialCreateResponseDTO {

    @Schema(description = "자재 ID", example = "123")
    private int materialId;

    @Schema(description = "자재명", example = "브레이크 패드 어셈블리")
    private String name;

    @Schema(description = "자재 코드", example = "FG-BRK-PAD")
    private String materialCode;

    @Schema(description = "자재 유형", example = "MAT_FG")
    private String type;

    @Schema(description = "단가", example = "45000")
    private Long unitPrice;

    @Schema(description = "자재 상태", example = "MAT_NORMAL")
    private String status;

    @Schema(description = "생성 일시", example = "2025-12-17 15:30:00")
    private String createdAt;

    /**
     * Material 엔티티를 MaterialCreateResponseDTO로 변환
     */
    public static MaterialCreateResponseDTO from(Material material) {
        return MaterialCreateResponseDTO.builder()
                .materialId(material.getId())
                .name(material.getName())
                .materialCode(material.getMaterialCode())
                .type(material.getType())
                .unitPrice(material.getUnitPrice())
                .status(material.getStatus())
                .createdAt(material.getCreatedAt())
                .build();
    }
}
