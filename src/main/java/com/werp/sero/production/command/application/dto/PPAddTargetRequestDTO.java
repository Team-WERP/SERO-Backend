package com.werp.sero.production.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PPAddTargetRequestDTO {
    @Schema(description = "생산요청 품목 ID", example = "123")
    private int prItemId;
}
