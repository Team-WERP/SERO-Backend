package com.werp.sero.production.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WOByDateResponseDTO {
    private int woId;
    private String woCode;
    private String woStatus;
    private String workDate;

    private int lineId;
    private String lineName;

    private String materialName;
    private String materialSpec;
    private String baseUnit;

    private int plannedQuantity;
}
