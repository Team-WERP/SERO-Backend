package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PPDetailResponseDTO {

    // PP
    private int ppId;
    private String ppCode;
    private String status;
    private String startDate;
    private String endDate;
    private int productionQuantity;
    private String createdAt;

    // PR
    private int prId;
    private String prCode;
    private int requestedQuantity;
    private String dueAt;

    // Item
    private int prItemId;
    private String itemCode;
    private String itemName;
    private String spec;
    private String unit;

    // Line
    private int productionLineId;
    private String productionLineName;
    private int dailyCapacity;

    // Manager
    private int managerId;
    private String managerName;
}

