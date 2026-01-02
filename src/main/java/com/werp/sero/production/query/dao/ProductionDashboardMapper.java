package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.dashboard.MaterialShortageResponseDTO;
import com.werp.sero.production.query.dto.dashboard.ProductionDashboardDefectAggDTO;
import com.werp.sero.production.query.dto.dashboard.ProductionLineCapaListDTO;
import com.werp.sero.production.query.dto.dashboard.ProductionMonthlyTrendRawDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductionDashboardMapper {
    /** 금일 목표 수량 (활성 라인 daily_capacity 합) */
    Integer selectTodayTargetQty();

    /** 금일 양품 수량 */
    Integer selectTodayGoodQty(@Param("workDate") String workDate);

    /** 금일 불량 집계 */
    ProductionDashboardDefectAggDTO selectTodayDefectAgg(@Param("workDate") String workDate);

    /** 금일 작업지시 전체 건수 */
    Integer selectTodayWorkOrderTotal(@Param("workDate") String workDate);

    /** 금일 가동 중(WO_RUN) 작업지시 */
    Integer selectTodayWorkOrderRunning(@Param("workDate") String workDate);

    Integer countWorkOrderByStatus(
            @Param("workDate") String workDate,
            @Param("status") String status
    );

    Integer selectTotalProductionLine();

    List<ProductionLineCapaListDTO> selectLineCapaRaw(
            @Param("today") String today
    );

    List<MaterialShortageResponseDTO> selectMaterialShortage();

    List<ProductionMonthlyTrendRawDTO> selectMonthlyProductionTrend();
}
