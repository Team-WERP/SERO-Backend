package com.werp.sero.production.query.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WorkOrderSummaryDTO {

    private int workOrderId;
    private String workOrderCode;
    private String status;
    private String createdAt;

    private int quantity;
    private List<WorkOrderItemDTO> items = new ArrayList<>();

    public static WorkOrderSummaryDTO from(WorkOrderDailyFlatDTO row) {
        WorkOrderSummaryDTO dto = new WorkOrderSummaryDTO();
        dto.workOrderId = row.getWorkOrderId();
        dto.workOrderCode = row.getWorkOrderCode();
        dto.status = row.getWorkOrderStatus();
        dto.createdAt = row.getWorkOrderCreatedAt();
        dto.quantity = row.getWorkOrderQuantity();
        return dto;
    }

    public void addItem(WorkOrderItemDTO item) {
        this.items.add(item);
    }
}

