package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderDailyFlatDTO {

    // Line
    private int lineId;
    private String lineName;
    private int dailyCapacity;

    // WorkOrder
    private int workOrderId;
    private String workOrderCode;
    private String workOrderStatus;
    private String workOrderCreatedAt;
    private int workOrderQuantity;

    // WorkOrderItem
    private Integer workOrderItemId;
    private int plannedQuantity;

    // PP (nullable)
    private Integer ppId;
    private String ppCode;

    // PR Item
    private int prItemId;
    private Integer prId;
    private String prCode;

    // Item
    private String itemCode;
    private String itemName;
    private String unit;
}

