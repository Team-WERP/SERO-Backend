package com.werp.sero.production.query.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.production.query.dao.ProductionDashboardMapper;
import com.werp.sero.production.query.dto.dashboard.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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

    @Override
    @Transactional(readOnly = true)
    public List<PrRiskResponseDTO> getPrRiskList() {

        // 생산요청 정보
        List<PrRiskRawDTO> prs =
                productionDashboardMapper.selectActivePrList();
        List<PrRiskItemRawDTO> items =
                productionDashboardMapper.selectPrItemRiskList();

        // 기준 정보
        Set<Integer> shortageMaterialIds = loadShortageMaterialIds();
        Map<String, Integer> lineLoadRateMap = loadLineLoadRateMap();

        Map<Integer, List<PrRiskItemRawDTO>> itemsByPr =
                items.stream().collect(groupingBy(PrRiskItemRawDTO::prId));

        List<PrRiskResponseDTO> result = new ArrayList<>();

        // 생산요청(PR)별 리스크 계산
        for (PrRiskRawDTO pr : prs) {

            List<PrRiskItemDTO> itemDtos = new ArrayList<>();
            int maxRisk = 0;

            for (PrRiskItemRawDTO item
                    : itemsByPr.getOrDefault(pr.prId(), List.of())) {

                PrRiskItemDTO dto =
                        calculateItemRisk(item, shortageMaterialIds, lineLoadRateMap);

                itemDtos.add(dto);
                maxRisk = Math.max(maxRisk, dto.riskScore());
            }

            result.add(buildPrResponse(pr, itemDtos, maxRisk));
        }

        return result;
    }

    /**
     * 계산 헬퍼 메소드
     */

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

    /* 생산요청 지연 리스크 계산용 헬퍼 메소드
    *  1. loadShortageMaterialIds : 자재 부족
    *  2. loadLineLoadRateMap : 라인 CAPA
    *  3. calculateItemRisk : Item 리스크 계산
    *  4. expectedProgress : 기대 진척률
    *  5. buildPrResponse : PR Response 조립
    * */

    private Set<Integer> loadShortageMaterialIds() {
        return productionDashboardMapper.selectMaterialShortage()
                .stream()
                .map(MaterialShortageResponseDTO::materialId)
                .collect(Collectors.toSet());
    }

    private Map<String, Integer> loadLineLoadRateMap() {
        return getLineCapa().stream()
                .collect(Collectors.toMap(
                        ProductionLineCapaItemDTO::lineName,
                        ProductionLineCapaItemDTO::loadRate
                ));
    }

    private PrRiskItemDTO calculateItemRisk(
            PrRiskItemRawDTO raw,
            Set<Integer> shortageMaterialIds,
            Map<String, Integer> lineLoadRateMap
    ) {
        int progressRate = raw.targetQty() == 0
                ? 0
                : (int) ((double) raw.producedQty() * 100 / raw.targetQty());

        int score = 0;

        // 생산 지연
        int expected = expectedProgress(raw);
        if (progressRate < expected - 30) {
            score += 40;          // 심각
        } else if (progressRate < expected - 15) {
            score += 25;          // 경고
        } else if (progressRate < expected) {
            score += 15;          // 주의
        }


        // 자재 부족
        boolean shortage = shortageMaterialIds.contains(raw.materialId());
        if (shortage) score += 30;

        // CAPA 초과
        int loadRate = lineLoadRateMap.getOrDefault(raw.lineName(), 0);
        boolean capaOver = loadRate > 100;
        if (capaOver) score += 20;

        return new PrRiskItemDTO(
                raw.prItemId(),
                raw.itemName(),
                raw.lineName(),
                raw.targetQty(),
                raw.producedQty(),
                progressRate,
                shortage ? "SHORTAGE" : "NORMAL",
                capaOver,
                score
        );
    }

    private int expectedProgress(PrRiskItemRawDTO raw) {

        long total = raw.totalDays();
        long elapsed = raw.elapsedDays();

        if (total <= 0) return 0;

        return (int) Math.min(100, elapsed * 100 / total) - 5;
    }

    private PrRiskResponseDTO buildPrResponse(
            PrRiskRawDTO pr,
            List<PrRiskItemDTO> items,
            int maxRisk
    ) {
        int dDay = pr.dDay();
        boolean delayExpected =
                (dDay <= 7 && maxRisk >= 40) || (dDay <= 3 && maxRisk >= 30);

        return new PrRiskResponseDTO(
                pr.prId(),
                pr.prCode(),
                pr.clientName(),
                pr.dueDate(),
                dDay,
                delayExpected,
                items,
                new PrRiskSummaryDTO(items.size(), maxRisk)
        );
    }


}
