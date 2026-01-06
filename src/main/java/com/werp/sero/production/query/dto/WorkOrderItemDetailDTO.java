package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderItemDetailDTO {

    // WO Item
    private int woItemId;
    private String status;

    // 생산요청 (PR)
    private int prId;
    private String prCode;

    // 생산계획 (PP, nullable)
    private Integer ppId;
    private String ppCode;

    // 품목
    private int soItemId;
    private String itemCode;
    private String itemName;
    private String unit;

    // 수량
    private int plannedQuantity;
    private int producedQuantity;
}

