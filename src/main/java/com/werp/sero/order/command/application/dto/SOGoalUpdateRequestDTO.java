package com.werp.sero.order.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "이달의 주문 목표 설정 DTO")
public class SOGoalUpdateRequestDTO {
    @Schema(description = "목표 수주 금액")
    private long goalAmount;
}
