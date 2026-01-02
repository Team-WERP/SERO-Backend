package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.dashboard.*;
import com.werp.sero.production.query.service.ProductionDashboardQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/production/dashboard")
public class ProductionDashboardQueryController {
    private final ProductionDashboardQueryService productionDashboardQueryService;

    @GetMapping("/summary")
    public ProductionDashboardSummaryResponseDTO summary() {
        return productionDashboardQueryService.getSummary();
    }

    @GetMapping("/line-status")
    public ProductionLineStatusResponseDTO lineStatus() {
        return productionDashboardQueryService.getLineStatus();
    }

    @GetMapping("/line-capa")
    public List<ProductionLineCapaItemDTO> lineCapa() {
        return productionDashboardQueryService.getLineCapa();
    }

    @GetMapping("/material-shortage")
    public List<MaterialShortageResponseDTO> materialShortage() {
        return productionDashboardQueryService.getMaterialShortage();
    }

    @GetMapping("/monthly-trend")
    public ProductionMonthlyTrendResponseDTO monthlyTrend() {
        return productionDashboardQueryService.getMonthlyTrend();
    }
}
