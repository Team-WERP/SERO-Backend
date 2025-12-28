package com.werp.sero.production.query.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PPValidateMapper {
    String selectMaterialCodeByPRItem(@Param("prItemId") int prItemId);

    int countPlansByPRItem(@Param("prItemId") int prItemId);

    Integer selectMaterialId(@Param("materialCode") String materialCode);

    Integer selectCycleTime(
            @Param("materialId") int materialId,
            @Param("lineId") int lineId
    );

    // 라인 + 날짜 기준 기존 계획 수량 합계
    int sumDailyPlannedQty(
            @Param("lineId") int lineId,
            @Param("date") String date
    );
}
