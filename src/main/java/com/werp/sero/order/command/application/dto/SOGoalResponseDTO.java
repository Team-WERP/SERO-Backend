package com.werp.sero.order.command.application.dto;

import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "이달의 주문 목표 응답 DTO")
public class SOGoalResponseDTO {
    @Schema(description = "목표 id")
    private int id;

    @Schema(description = "년도")
    private int goalYear;

    @Schema(description = "월")
    private int goalMonth;

    @Schema(description = "목표 수주 금액")
    private long goalAmount;

    public static SOGoalResponseDTO of(SalesOrderGoal goal) {
        return SOGoalResponseDTO.builder()
                .id(goal.getId())
                .goalYear(goal.getGoalYear())
                .goalMonth(goal.getGoalMonth())
                .goalAmount(goal.getGoalAmount())
                .build();
    }

}
