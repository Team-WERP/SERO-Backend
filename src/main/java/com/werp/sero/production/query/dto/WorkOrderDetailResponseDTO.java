package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkOrderDetailResponseDTO {

    // 기본 정보
    private int woId;
    private String woCode;
    private String status;

    private int lineId;
    private String lineName;

    private String managerName;

    // 실적 요약
    private int plannedQuantity;
    private int goodQuantity;
    private int defectiveQuantity;
    private int workTime;

    private String startTime;
    private String endTime;

    // 하위 상세
    private List<WorkOrderItemDetailDTO> items;
    private List<WorkOrderMaterialDetailDTO> materials;
    private List<WorkOrderHistoryResponse> histories;
}

