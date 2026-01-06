package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.*;

import java.util.List;

public interface WOQueryService {

    List<WOByDateResponseDTO> getByDate(String date);

    List<WorkOrderHistoryResponse> getHistory(int woId);

    WorkOrderResultResponse getResult(int woId);

    List<WorkOrderDailyResponseDTO> getDailyWorkOrders(String date);
}
