package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.WorkOrderResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WOQueryMapper {
    List<WorkOrderResponseDTO> selectByProductionPlan(int ppId);
}
