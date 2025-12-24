package com.werp.sero.production.query.dao;

import com.werp.sero.production.command.application.dto.PPMonthlyPlanResponseDTO;
import com.werp.sero.production.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PPQueryMapper {
    PRItemPlanningBaseDTO selectPRItemPlanningBase(
            @Param("prItemId") int prItemId
    );

    List<ProductionPlanSummaryDTO> selectProductionPlansByPRItem(
            @Param("prItemId") int prItemId
    );

    List<ProductionLineResponseDTO> selectProductionLines(
            @Param("factoryId") Integer factoryId
    );

    List<PPUnassignedResponseDTO> selectUnassignedTargets();

    List<PPMonthlyPlanResponseDTO> selectMonthlyPlans(
            @Param("monthStart") String monthStart,
            @Param("monthEnd") String monthEnd
    );

    List<PPDailyPreviewResponseDTO> selectDailyPreview(String date);
}
