package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WOEmergencyPRItemResponseDTO {

    private int prItemId;
    private int prId;
    private String prCode;

    private String itemCode;
    private String itemName;
    private String unit;

    private int remainingQuantity; // 요청 - 생산완료
    private String dueAt;
}

