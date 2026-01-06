package com.werp.sero.production.query.dto;

import lombok.Getter;

@Getter
public class WorkOrderItemDTO {

    private String type;   // PP | PR
    private Integer ppId;
    private String ppCode;
    private int prItemId;

    private String itemCode;
    private String itemName;
    private String unit;

    private int plannedQuantity;

    public static WorkOrderItemDTO from(WorkOrderDailyFlatDTO row) {
        WorkOrderItemDTO dto = new WorkOrderItemDTO();
        dto.ppId = row.getPpId();
        dto.ppCode = row.getPpCode();
        dto.prItemId = row.getPrItemId();
        dto.itemCode = row.getItemCode();
        dto.itemName = row.getItemName();
        dto.unit = row.getUnit();
        dto.plannedQuantity = row.getPlannedQuantity();
        dto.type = (row.getPpId() != null) ? "PP" : "PR";
        return dto;
    }
}

