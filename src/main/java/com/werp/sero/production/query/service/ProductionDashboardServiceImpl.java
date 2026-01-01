package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dao.ProductionDashboardMapper;
import com.werp.sero.production.query.dto.ProductionDashboardKpiResponse;
import com.werp.sero.production.query.dto.WorkOrderCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductionDashboardServiceImpl implements ProductionDashboardService{
    private final ProductionDashboardMapper productionDashboardMapper;

    @Override
    public ProductionDashboardKpiResponse getKpi() {

        LocalDate today = LocalDate.now();

        int plannedQty = productionDashboardMapper.selectTodayPlannedQty(today);
        int producedQty = productionDashboardMapper.selectTodayProducedQty(today);

        double achievementRate =
                plannedQty == 0 ? 0.0 : (double) producedQty / plannedQty * 100;

        WorkOrderCountDTO woCount = productionDashboardMapper.selectTodayWorkOrderCount(today);
        int delayRiskCount = productionDashboardMapper.selectDelayRiskCount(today);

        return new ProductionDashboardKpiResponse(
                today.toString(),
                new ProductionDashboardKpiResponse.TodayProduction(
                        plannedQty,
                        producedQty,
                        Math.round(achievementRate * 10) / 10.0
                ),
                new ProductionDashboardKpiResponse.WorkOrderSummary(
                        woCount.getTotal(),
                        woCount.getRunning(),
                        woCount.getPaused(),
                        woCount.getDone()
                ),
                delayRiskCount
        );
    }
}
