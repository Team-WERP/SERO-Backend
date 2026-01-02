package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.dashboard.ProductionDashboardSummaryResponseDTO;
import com.werp.sero.production.query.dto.dashboard.ProductionLineStatusResponseDTO;

public interface ProductionDashboardQueryService {
    ProductionDashboardSummaryResponseDTO getSummary();

    ProductionLineStatusResponseDTO getLineStatus();
}
