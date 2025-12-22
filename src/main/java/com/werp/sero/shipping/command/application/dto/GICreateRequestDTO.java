package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GICreateRequestDTO {

    @NotNull(message = "납품서 번호는 필수입니다")
    @Schema(description = "납품서 번호", example = "DO-20251220-001")
    private String doCode;

    @NotNull(message = "출고 창고는 필수입니다")
    @Schema(description = "출고 창고 ID", example = "1")
    private Integer warehouseId;

    @Schema(description = "특이사항", example = "주의사항 없음")
    private String note;
}
