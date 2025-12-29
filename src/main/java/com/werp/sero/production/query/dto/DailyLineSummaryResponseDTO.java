package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class DailyLineSummaryResponseDTO {

    private int productionLineId;
    private String productionLineName;
    private int dailyCapacity; // 라인 1일 최대 생산 가능 수량

    /**
     * key   : day of month (1~31)
     * value : 해당 날짜의 계획 수량 합계
     */
    private Map<Integer, Integer> dailyPlannedQtyMap;
}
