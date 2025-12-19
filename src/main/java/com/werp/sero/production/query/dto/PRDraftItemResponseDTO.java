package com.werp.sero.production.query.dto;

import lombok.Getter;

@Getter
public class PRDraftItemResponseDTO {

    private int soItemId;

    private String itemCode;
    private String itemName;
    private String spec;

    private int orderQuantity;
    private String unit;

    private int unitPrice;
    private long totalPrice;

    private int availableStock;      // 가용재고
    private int requiredQuantity;    // 생산필요량 = 주문 - 재고 (최소 0)

    private int requestedQuantity;   // 임시저장된 생산요청 수량
}

