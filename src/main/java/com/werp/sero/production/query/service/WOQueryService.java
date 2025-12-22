package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.WorkOrderByPRResponseDTO;
import com.werp.sero.production.query.dto.WorkOrderByPPResponseDTO;

import java.util.List;

public interface WOQueryService {
    List<WorkOrderByPPResponseDTO> getByProductionPlan(int ppId);

    List<WorkOrderByPRResponseDTO> getByProductionRequest(int prId);
}
