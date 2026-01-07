package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LineMaterialListItemDTO {

    private int lineMaterialId;

    private int materialId;
    private String materialCode;
    private String materialName;

    private int lineId;
    private String lineName;

    private int cycleTime;
}

