package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.WorkOrderCountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface ProductionDashboardMapper {
    int selectTodayPlannedQty(LocalDate date);

    int selectTodayProducedQty(LocalDate date);

    WorkOrderCountDTO selectTodayWorkOrderCount(LocalDate date);

    int selectDelayRiskCount(LocalDate today);
}
