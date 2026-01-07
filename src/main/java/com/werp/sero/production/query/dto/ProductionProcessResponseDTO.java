package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductionProcessResponseDTO {

    private int processId;
    private int processOrder;
    private String processName;

    private String lineName;
    private int standardTime;
    private int headCount;

    private String note;
}
