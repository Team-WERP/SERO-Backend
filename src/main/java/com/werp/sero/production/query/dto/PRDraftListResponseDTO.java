package com.werp.sero.production.query.dto;

import lombok.Getter;

@Getter
public class PRDraftListResponseDTO {
    private int prId;
    private String soCode;
    private String clientName;
    private int totalQuantity;
    private String dueAt;
    private String createdAt;
    private String representativeItemName;
    private int itemTypeCount;
}
