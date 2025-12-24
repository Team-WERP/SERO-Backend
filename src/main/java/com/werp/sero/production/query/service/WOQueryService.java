package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.*;

import java.util.List;

public interface WOQueryService {
    List<WOByPPResponseDTO> getByProductionPlan(int ppId);

    List<WOByPRResponseDTO> getByProductionRequest(int prId);

    List<WOByDateResponseDTO> getByDate(String date);

    List<WorkOrderHistoryResponse> getHistory(int woId);

    WorkOrderResultResponse getResult(int woId);
}
