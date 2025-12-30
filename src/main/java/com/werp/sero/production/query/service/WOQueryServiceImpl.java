package com.werp.sero.production.query.service;

import com.werp.sero.production.command.domain.aggregate.WorkOrder;
import com.werp.sero.production.command.domain.aggregate.WorkOrderHistory;
import com.werp.sero.production.command.domain.aggregate.WorkOrderResult;
import com.werp.sero.production.command.domain.repository.WorkOrderHistoryRepository;
import com.werp.sero.production.command.domain.repository.WorkOrderResultRepository;
import com.werp.sero.production.exception.WorkOrderResultNotFoundException;
import com.werp.sero.production.query.dao.WOQueryMapper;
import com.werp.sero.production.query.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WOQueryServiceImpl implements WOQueryService{
    private final WOQueryMapper woQueryMapper;
    private final WorkOrderHistoryRepository woHistoryRepository;
    private final WorkOrderResultRepository workOrderResultRepository;

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

    @Override
    @Transactional(readOnly = true)
    public WorkOrderResultResponse getResult(int woId) {

        WorkOrderResult result = workOrderResultRepository.findByWorkOrder_Id(woId)
                .orElseThrow(WorkOrderResultNotFoundException::new);

        return new WorkOrderResultResponse(
                result.getWorkOrder().getWoCode(),
                result.getGoodQuantity(),
                result.getDefectiveQuantity(),
                result.getStartDate(),
                result.getEndDate(),
                result.getWorkTime(),
                result.getNote()
        );
    }


}
