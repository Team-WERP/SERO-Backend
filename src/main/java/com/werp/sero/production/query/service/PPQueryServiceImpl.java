package com.werp.sero.production.query.service;

import com.werp.sero.production.exception.ProductionRequestItemNotFoundException;
import com.werp.sero.production.query.dao.PPQueryMapper;
import com.werp.sero.production.query.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PPQueryServiceImpl implements PPQueryService{
    private final PPQueryMapper ppQueryMapper;

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
}
