package com.werp.sero.production.query.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.production.command.application.dto.PPMonthlyPlanResponseDTO;
import com.werp.sero.production.command.domain.aggregate.ProductionLine;
import com.werp.sero.production.command.domain.repository.ProductionLineRepository;
import com.werp.sero.production.exception.ProductionRequestItemNotFoundException;
import com.werp.sero.production.query.dao.PPQueryMapper;
import com.werp.sero.production.query.dao.PPValidateMapper;
import com.werp.sero.production.query.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PPQueryServiceImpl implements PPQueryService{
    private final PPQueryMapper ppQueryMapper;
    private final PPValidateMapper ppValidateMapper;

    @Override
    @Transactional(readOnly = true)
    public PRItemPlanningResponseDTO getPlanning(int prItemId) {

        PRItemPlanningBaseDTO base = Optional.ofNullable(
                ppQueryMapper.selectPRItemPlanningBase(prItemId)
        ).orElseThrow(ProductionRequestItemNotFoundException::new);

        List<ProductionPlanSummaryDTO> plans =
                ppQueryMapper.selectProductionPlansByPRItem(prItemId);

        int plannedQuantity = plans.stream()
                .mapToInt(ProductionPlanSummaryDTO::getProductionQuantity)
                .sum();

        int remainingQuantity = base.getRequestedQuantity() - plannedQuantity;

        PRItemPlanningResponseDTO response = new PRItemPlanningResponseDTO(
                base.getPrItemId(),
                base.getItemCode(),
                base.getItemName(),
                base.getRequestedQuantity(),
                plannedQuantity,
                remainingQuantity,
                plans
        );

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionLineResponseDTO> getProductionLines(Integer factoryId) {
        return ppQueryMapper.selectProductionLines(factoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PPUnassignedResponseDTO> getUnassignedTargets(String month) {
        return ppQueryMapper.selectUnassignedTargets();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PPMonthlyPlanResponseDTO> getMonthlyPlans(String month) {
        YearMonth ym = DateTimeUtils.parseYearMonth(month);
        String monthStart = ym.atDay(1).toString();  // yyyy-MM-01
        String monthEnd   = ym.atEndOfMonth().toString(); // yyyy-MM-28~31

        return ppQueryMapper.selectMonthlyPlans(monthStart, monthEnd);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PPDailyPreviewResponseDTO> getDailyPreview(String date) {
        return ppQueryMapper.selectDailyPreview(date);
    }

    @Override
    public List<DailyLineSummaryResponseDTO> getDailyLineSummary(String month, Integer factoryId) {
        YearMonth ym = YearMonth.parse(month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        // 가동 가능한 라인 조회 (dailyCapacity 포함)
        List<ProductionLineResponseDTO> lines = ppQueryMapper.selectProductionLines(factoryId);
        List<DailyLineSummaryResponseDTO> result = new ArrayList<>();

        // 라인별, 날짜별 계획 수량 집계
        for (ProductionLineResponseDTO line : lines) {
            Map<Integer, Integer> dailyMap = new HashMap<>();

            LocalDate date = start;
            while (!date.isAfter(end)) {
                int plannedQty = ppValidateMapper.sumDailyPlannedQty(
                        line.getLineId(),
                        date.toString() // yyyy-MM-dd
                );
                dailyMap.put(date.getDayOfMonth(), plannedQty);
                date = date.plusDays(1);
            }

            result.add(
                    new DailyLineSummaryResponseDTO(
                            line.getLineId(),
                            line.getLineName(),
                            line.getDailyCapacity(),
                            dailyMap
                    )
            );
        }

        return result;
    }
}
