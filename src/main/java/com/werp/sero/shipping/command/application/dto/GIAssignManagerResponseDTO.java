package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GIAssignManagerResponseDTO {

    @Schema(description = "출고지시 코드", example = "GI-20251220-001")
    private String giCode;

    @Schema(description = "배정된 담당자 ID", example = "5")
    private int managerId;

    @Schema(description = "배정된 담당자 이름", example = "김물류")
    private String managerName;

    @Schema(description = "배정된 담당자 부서", example = "물류팀")
    private String managerDepartment;

    @Schema(description = "배정 시각", example = "2025-12-20 14:30")
    private String assignedAt;
}
