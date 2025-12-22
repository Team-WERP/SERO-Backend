package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dao.WOQueryMapper;
import com.werp.sero.production.query.dto.WorkOrderByPRResponseDTO;
import com.werp.sero.production.query.dto.WorkOrderByPPResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WOQueryServiceImpl implements WOQueryService{
    private final WOQueryMapper woQueryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderByPPResponseDTO> getByProductionPlan(int ppId) {
        return woQueryMapper.selectByProductionPlan(ppId);
    }

    @Override
    public List<WorkOrderByPRResponseDTO> getByProductionRequest(int prId) {
        return woQueryMapper.selectByProductionRequest(prId);
    }
}
