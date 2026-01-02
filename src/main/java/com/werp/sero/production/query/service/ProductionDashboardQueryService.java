package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.dashboard.*;

import java.util.List;

public interface ProductionDashboardQueryService {
    ProductionDashboardSummaryResponseDTO getSummary();

    ProductionLineStatusResponseDTO getLineStatus();

    List<ProductionLineCapaItemDTO> getLineCapa();

    List<MaterialShortageResponseDTO> getMaterialShortage();

    ProductionMonthlyTrendResponseDTO getMonthlyTrend();
}
