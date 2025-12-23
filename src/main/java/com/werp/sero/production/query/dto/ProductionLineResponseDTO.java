package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ProductionLineResponseDTO {
    private int lineId;
    private String lineName;
    private String status;
}
