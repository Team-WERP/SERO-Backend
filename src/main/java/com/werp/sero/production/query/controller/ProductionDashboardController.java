package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.ProductionDashboardKpiResponse;
import com.werp.sero.production.query.service.ProductionDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard/production")
public class ProductionDashboardController {

    private final ProductionDashboardService productionDashboardService;

    @GetMapping("/kpi")
    public ProductionDashboardKpiResponse getKpi() {
        return productionDashboardService.getKpi();
    }
}
