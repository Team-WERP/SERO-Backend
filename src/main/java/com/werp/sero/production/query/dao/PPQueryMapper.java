package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.PRItemPlanningBaseDTO;
import com.werp.sero.production.query.dto.ProductionLineResponseDTO;
import com.werp.sero.production.query.dto.ProductionPlanSummaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
