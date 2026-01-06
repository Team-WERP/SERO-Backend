package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.WOByDateResponseDTO;
import com.werp.sero.production.query.dto.WOByPRResponseDTO;
import com.werp.sero.production.query.dto.WOByPPResponseDTO;
import com.werp.sero.production.query.dto.WorkOrderDailyFlatDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WOQueryMapper {

    List<WOByDateResponseDTO> selectByDate(String date);

    List<WorkOrderDailyFlatDTO> selectDailyWorkOrders(
            @Param("date") String date
    );

}
