package com.werp.sero.production.query.dto;

import lombok.Getter;

@Getter
public class WorkOrderItemDTO {

    private String type;   // PP | PR

    // PP
    private Integer ppId;
    private String ppCode;

    // PR
    private Integer prItemId;
    private Integer prId;
    private String prCode;

    // Item
    private String itemCode;
    private String itemName;
    private String unit;

    private int plannedQuantity;

    public static WorkOrderItemDTO from(WorkOrderDailyFlatDTO row) {
        WorkOrderItemDTO dto = new WorkOrderItemDTO();
        dto.ppId = row.getPpId();
        dto.ppCode = row.getPpCode();
        dto.prItemId = row.getPrItemId();
        dto.prId = row.getPrId();
        dto.prCode = row.getPrCode();
        dto.itemCode = row.getItemCode();
        dto.itemName = row.getItemName();
        dto.unit = row.getUnit();
        dto.plannedQuantity = row.getPlannedQuantity();
        dto.type = (row.getPpId() != null) ? "PP" : "PR";
        return dto;
    }
}

