package com.werp.sero.production.command.application.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WorkOrderEndRequest {
    private int goodQuantity;
    private int defectiveQuantity;

    private String startTime; // yyyy-MM-dd HH:mm:ss
    private String endTime;

    private String note;

    private List<ItemResult> items;
    private List<MaterialResult> materials;

    @Getter
    public static class MaterialResult {
        private int materialId;
        private int actualQuantity;
    }

    @Getter
    public static class ItemResult {
        private int workOrderItemId;
        private int producedQuantity;
    }
}
