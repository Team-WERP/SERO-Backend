package com.werp.sero.production.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WOItemByConditionResponseDTO {

    private int woId;
    private String woCode;
    private String woStatus;
    private String workDate;

    private int woItemId;

    private int prId;
    private String prCode;

    private int ppId;
    private String ppCode;
    private String ppStatus;

    private int lineId;
    private String lineName;

    private int plannedQuantity;
    private int producedQuantity;
}

