package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderItemPreviewDTO {

    private int workOrderItemId;
    private String itemName;
    private int plannedQuantity;
    private int producedQuantity;
}

