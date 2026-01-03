package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.dashboard.*;
import com.werp.sero.production.query.service.ProductionDashboardQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "생산 대시보드",
        description = "생산 현황, 라인 가동 상태, 생산 리스크 등을 조회하는 대시보드용 조회 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/production/dashboard")
public class ProductionDashboardQueryController {
    private final ProductionDashboardQueryService productionDashboardQueryService;

    @Operation(
            summary = "생산 대시보드 요약 조회",
            description = "금일 생산 목표 대비 실적, 양품 수량, 불량률 등 대시보드 상단 KPI 요약 정보를 조회합니다."
    )
    @GetMapping("/summary")
    public ProductionDashboardSummaryResponseDTO summary() {
        return productionDashboardQueryService.getSummary();
    }

    @Operation(
            summary = "생산 라인 가동 상태 조회",
            description = "가동 중, 일시 정지, 비가동 상태의 생산 라인 현황을 집계하여 조회합니다."
    )
    @GetMapping("/line-status")
    public ProductionLineStatusResponseDTO lineStatus() {
        return productionDashboardQueryService.getLineStatus();
    }

    @Operation(
            summary = "생산 라인별 일일 생산능력 조회",
            description = "각 생산 라인의 일일 최대 생산 가능 수량(일일 CAPA) 정보를 조회합니다."
    )
    @GetMapping("/line-capa")
    public List<ProductionLineCapaItemDTO> lineCapa() {
        return productionDashboardQueryService.getLineCapa();
    }

    @Operation(
            summary = "자재 부족 현황 조회",
            description = "현재 생산 요청 및 계획 기준으로 자재 부족이 예상되는 항목 목록을 조회합니다."
    )
    @GetMapping("/material-shortage")
    public List<MaterialShortageResponseDTO> materialShortage() {
        return productionDashboardQueryService.getMaterialShortage();
    }

    @Operation(
            summary = "월별 생산 추이 조회",
            description = "최근 월별 생산 실적 추이를 집계하여 차트 표시용 데이터로 조회합니다."
    )
    @GetMapping("/monthly-trend")
    public ProductionMonthlyTrendResponseDTO monthlyTrend() {
        return productionDashboardQueryService.getMonthlyTrend();
    }

    @Operation(
            summary = "생산 요청(PR) 리스크 목록 조회",
            description = "납기 지연 가능성이 있는 생산 요청(PR) 목록을 리스크 기준으로 조회합니다."
    )
    @GetMapping("/pr-risk")
    public List<PrRiskResponseDTO> prRisk() {
        return productionDashboardQueryService.getPrRiskList();
    }

}
