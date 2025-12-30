package com.werp.sero.deadline.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineMaterialInfo {
    private int lineMaterialId;
    private int productionLineId;
    private String productionLineName;
    private String productionLineStatus;
    private int dailyCapacity;
}
