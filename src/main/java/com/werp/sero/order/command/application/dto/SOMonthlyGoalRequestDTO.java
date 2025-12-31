package com.werp.sero.order.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class SOMonthlyGoalRequestDTO {

    @Schema(description = "년도")
    private int year;

    @Schema(description = "월")
    private int month;

    @Schema(description = "목표 금액")
    private long goalAmount;

    @Schema(description = "생성일시")
    private String createdAt;

    @Schema(description = "수정일시")
    private String updatedAt;
}
