package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WOQueryMapper {

    List<WOByDateResponseDTO> selectByDate(String date);

    List<WorkOrderDailyFlatDTO> selectDailyWorkOrders(
            @Param("date") String date
    );

    List<WOEmergencyPRItemResponseDTO> selectEmergencyTargetsByLine(int lineId);

    List<WorkOrderResultListResponseDTO> selectWorkOrderResultList(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("lineId") Integer lineId,
            @Param("keyword") String keyword
    );

    WorkOrderBaseDetailDTO selectWorkOrderBaseDetail(@Param("woId") int woId);

    List<WorkOrderItemDetailDTO> selectWorkOrderItemDetails(@Param("woId") int woId);

    List<WorkOrderMaterialDetailDTO> selectWorkOrderMaterialDetails(@Param("woId") int woId);

}
