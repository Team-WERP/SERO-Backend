package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.dashboard.ProductionDashboardSummaryResponseDTO;
import com.werp.sero.production.query.dto.dashboard.ProductionLineCapaItemDTO;
import com.werp.sero.production.query.dto.dashboard.ProductionLineStatusResponseDTO;

import java.util.List;

public interface ProductionDashboardQueryService {
    ProductionDashboardSummaryResponseDTO getSummary();

    ProductionLineStatusResponseDTO getLineStatus();

    List<ProductionLineCapaItemDTO> getLineCapa();
}
