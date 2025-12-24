package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PPUnassignedResponseDTO {
    private int prItemId;
    private String itemName;
    private String spec;
    private int requestedQuantity;
    private String dueAt;

    private int lineMaterialId;
    private int productionLineId;
    private String productionLineName;

}
