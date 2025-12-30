package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderResultPreviewRequestDTO {
    private int goodQuantity;
    private int defectiveQuantity;
}
