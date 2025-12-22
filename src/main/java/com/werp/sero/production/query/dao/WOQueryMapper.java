package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.WorkOrderByPRResponseDTO;
import com.werp.sero.production.query.dto.WorkOrderByPPResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WOQueryMapper {
    List<WorkOrderByPPResponseDTO> selectByProductionPlan(int ppId);

    List<WorkOrderByPRResponseDTO> selectByProductionRequest(int prId);
}
