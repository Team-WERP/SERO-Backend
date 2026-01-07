package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkOrderResultPreviewResponseDTO {
    private int totalGoodQuantity;
    private List<WorkOrderItemPreviewDTO> items;
    private List<WorkOrderMaterialPreviewDTO> materials;
}
