package com.werp.sero.production.query.service;

import com.werp.sero.production.command.domain.aggregate.WorkOrderHistory;
import com.werp.sero.production.command.domain.repository.WorkOrderHistoryRepository;
import com.werp.sero.production.query.dao.WOQueryMapper;
import com.werp.sero.production.query.dto.WOByDateResponseDTO;
import com.werp.sero.production.query.dto.WOByPRResponseDTO;
import com.werp.sero.production.query.dto.WOByPPResponseDTO;
import com.werp.sero.production.query.dto.WorkOrderHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WOQueryServiceImpl implements WOQueryService{
    private final WOQueryMapper woQueryMapper;
    private final WorkOrderHistoryRepository woHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WOByPPResponseDTO> getByProductionPlan(int ppId) {
        return woQueryMapper.selectByProductionPlan(ppId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WOByPRResponseDTO> getByProductionRequest(int prId) {
        return woQueryMapper.selectByProductionRequest(prId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WOByDateResponseDTO> getByDate(String date) {
        return woQueryMapper.selectByDate(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderHistoryResponse> getHistory(int woId) {
        List<WorkOrderHistory> histories
                = woHistoryRepository.findByWorkOrder_IdOrderByActedAtAsc(woId);
        return histories.stream()
                .map(h -> new WorkOrderHistoryResponse(
                        h.getAction(),
                        h.getActedAt(),
                        h.getNote()
                ))
                .toList();
    }


}
