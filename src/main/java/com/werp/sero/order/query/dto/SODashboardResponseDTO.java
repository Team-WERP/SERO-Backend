package com.werp.sero.order.query.dto;

import com.werp.sero.order.command.domain.aggregate.SalesOrderGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 대시보드 통합 응답 DTO")
public class SODashboardResponseDTO {
    @Schema(description = "대시보드 데이터")
    private SummaryStats stats;

    @Schema(description = "월별 수주 실적 및 목표 비교 데이터")
    private List<SOGoalDTO> monthlyGoal;

    @Schema(description = "거래처별 데이터")
    private List<ClientTopStats> topClients;

    @Schema(description = "납기 임박 데이터")
    private List<SOUrgentOrderDTO> urgentOrders;


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SummaryStats {
        @Schema(description = "이번달 총 주문 건수")
        private long totalOrderCount;

        @Schema(description = "전월 대비 주문 건 증감 수")
        private int orderCountDiff;

        @Schema(description = "이번달 신규 주문 금액")
        private long newOrderAmount;

        @Schema(description = "전월 대비 금액 증감률 (%)")
        private double amountDiffRate;

        @Schema(description = "이번달 목표 달성률 (%)")
        private double goalAchieveRate;

        @Schema(description = "이번달 출고 실적 금액")
        private long shipmentPerformance;

        @Schema(description = "미확인 주문 건수")
        private int pendingOrderCount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientTopStats {
        @Schema(description = "고객사 이름")
        private String clientName;

        @Schema(description = "총 금액")
        private long totalAmount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SOGoalDTO {

        @Schema(description = "년도")
        private int goalYear;

        @Schema(description = "월")
        private int goalMonth;

        @Schema(description = "목표 금액")
        private long goalAmount;

        @Schema(description = "실제 수주액")
        private long actualAmount;

    }

}