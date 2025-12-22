package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.WorkOrderResponseDTO;

import java.util.List;

public interface WOQueryService {
    List<WorkOrderResponseDTO> getByProductionPlan(int ppId);
}
