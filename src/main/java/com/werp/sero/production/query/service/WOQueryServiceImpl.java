package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dao.WOQueryMapper;
import com.werp.sero.production.query.dto.WOByDateResponseDTO;
import com.werp.sero.production.query.dto.WOByPRResponseDTO;
import com.werp.sero.production.query.dto.WOByPPResponseDTO;
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
    public List<WOByPPResponseDTO> getByProductionPlan(int ppId) {
        return woQueryMapper.selectByProductionPlan(ppId);
    }

    @Override
    public List<WOByPRResponseDTO> getByProductionRequest(int prId) {
        return woQueryMapper.selectByProductionRequest(prId);
    }

    @Override
    public List<WOByDateResponseDTO> getByDate(String date) {
        return woQueryMapper.selectByDate(date);
    }


}
