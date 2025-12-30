package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출고지시 담당자 배정 요청 DTO")
public class GIManagerRequestDTO {

    @NotNull(message = "담당자 ID는 필수입니다.")
    @Schema(description = "본사 직원 id")
    private int empId;
}
