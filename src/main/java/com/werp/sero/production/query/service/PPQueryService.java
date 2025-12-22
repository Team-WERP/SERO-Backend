package com.werp.sero.production.query.service;

import com.werp.sero.production.command.application.dto.PPMonthlyPlanResponseDTO;
import com.werp.sero.production.query.dto.PPUnassignedResponseDTO;
import com.werp.sero.production.query.dto.PRItemPlanningResponseDTO;
import com.werp.sero.production.query.dto.ProductionLineResponseDTO;

import java.util.List;

public interface PPQueryService {
    PRItemPlanningResponseDTO getPlanning(int prItemId);

    List<ProductionLineResponseDTO> getProductionLines(Integer factoryId);

    List<PPUnassignedResponseDTO> getUnassignedTargets(String month);

    List<PPMonthlyPlanResponseDTO> getMonthlyPlans(String month);
}
