package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.dashboard.ProductionDashboardSummaryResponseDTO;
import com.werp.sero.production.query.dto.dashboard.ProductionLineStatusResponseDTO;
import com.werp.sero.production.query.service.ProductionDashboardQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
