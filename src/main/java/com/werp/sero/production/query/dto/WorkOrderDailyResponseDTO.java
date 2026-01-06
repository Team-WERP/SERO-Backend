package com.werp.sero.production.query.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WorkOrderDailyResponseDTO {

    private int lineId;
    private String lineName;
    private int dailyCapacity;

    private List<WorkOrderSummaryDTO> workOrders = new ArrayList<>();

    /* ===== factory ===== */
    public static WorkOrderDailyResponseDTO from(WorkOrderDailyFlatDTO row) {
        WorkOrderDailyResponseDTO dto = new WorkOrderDailyResponseDTO();
        dto.lineId = row.getLineId();
        dto.lineName = row.getLineName();
        dto.dailyCapacity = row.getDailyCapacity();
        return dto;
    }

    /* ===== helper ===== */
    public WorkOrderSummaryDTO getOrCreateWorkOrder(WorkOrderDailyFlatDTO row) {
        return workOrders.stream()
                .filter(wo -> wo.getWorkOrderId() == row.getWorkOrderId())
                .findFirst()
                .orElseGet(() -> {
                    WorkOrderSummaryDTO wo = WorkOrderSummaryDTO.from(row);
                    workOrders.add(wo);
                    return wo;
                });
    }
}

