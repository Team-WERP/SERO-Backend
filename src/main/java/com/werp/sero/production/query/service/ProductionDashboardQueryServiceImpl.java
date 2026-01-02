package com.werp.sero.production.query.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.production.query.dao.ProductionDashboardMapper;
import com.werp.sero.production.query.dto.dashboard.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionDashboardQueryServiceImpl implements ProductionDashboardQueryService {

    private final ProductionDashboardMapper productionDashboardMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductionDashboardSummaryResponseDTO getSummary() {

        String today = DateTimeUtils.nowDate();

        // 목표 수량
        int targetQty = nvl(productionDashboardMapper.selectTodayTargetQty());

        // 실적 수량
        int completedQty = nvl(productionDashboardMapper.selectTodayGoodQty(today));

        // 불량 집계
        ProductionDashboardDefectAggDTO defectAgg =
                productionDashboardMapper.selectTodayDefectAgg(today);

        int goodQty = defectAgg == null ? 0 : defectAgg.goodQty();
        int defectiveQty = defectAgg == null ? 0 : defectAgg.defectiveQty();

        double defectRate = percent(defectiveQty, goodQty + defectiveQty);

        // 달성률
        double achievementRate = percent(completedQty, targetQty);

        // 라인 가동률
        int totalWo = nvl(productionDashboardMapper.selectTodayWorkOrderTotal(today));
        int runningWo = nvl(productionDashboardMapper.selectTodayWorkOrderRunning(today));
        double lineUtilizationRate = percent(runningWo, totalWo);

        return new ProductionDashboardSummaryResponseDTO(
                round1(achievementRate),
                completedQty,
                targetQty,
                round1(defectRate),
                round1(lineUtilizationRate)
        );

    }

    @Override
    @Transactional(readOnly = true)
    public ProductionLineStatusResponseDTO getLineStatus() {
        String today = DateTimeUtils.nowDate();
        int running =
                nvl(productionDashboardMapper.countWorkOrderByStatus(today, "WO_RUN"));

        int paused =
                nvl(productionDashboardMapper.countWorkOrderByStatus(today, "WO_PAUSE"));

        int totalLine =
                nvl(productionDashboardMapper.selectTotalProductionLine());

        int activeLine = running + paused;
        int stopped = Math.max(totalLine - activeLine, 0);

        return new ProductionLineStatusResponseDTO(
                running,
                paused,
                stopped
        );

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionLineCapaItemDTO> getLineCapa() {
        String today = DateTimeUtils.nowDate();

        List<ProductionLineCapaListDTO> raws =
                productionDashboardMapper.selectLineCapaRaw(today);

        List<ProductionLineCapaItemDTO> lines = raws.stream()
                .map(raw -> {
                    int capacity = raw.dailyCapacity();
                    int load = raw.plannedLoad();

                    int loadRate = capacity <= 0
                            ? 0
                            : (int) Math.round((double) load * 100 / capacity);

                    return new ProductionLineCapaItemDTO(
                            raw.lineId(),
                            raw.lineName(),
                            load,
                            capacity,
                            loadRate
                    );
                })
                .toList();

        return lines;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialShortageResponseDTO> getMaterialShortage() {
        List<MaterialShortageResponseDTO> items =
                productionDashboardMapper.selectMaterialShortage();

        return items;
    }

    @Override
    public ProductionMonthlyTrendResponseDTO getMonthlyTrend() {
        List<ProductionMonthlyTrendRawDTO> raws =
                productionDashboardMapper.selectMonthlyProductionTrend();

        List<String> labels = new ArrayList<>();
        List<Integer> target = new ArrayList<>();
        List<Integer> actual = new ArrayList<>();

        for (ProductionMonthlyTrendRawDTO raw : raws) {
            labels.add(raw.month());
            target.add(raw.targetQuantity());
            actual.add(raw.actualQuantity());
        }

        return new ProductionMonthlyTrendResponseDTO(labels, target, actual);
    }

    private int nvl(Integer value) {
        return value == null ? 0 : value;
    }

    private double percent(int numerator, int denominator) {
        if (denominator <= 0) return 0.0;
        return (double) numerator * 100.0 / denominator;
    }

    private double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
