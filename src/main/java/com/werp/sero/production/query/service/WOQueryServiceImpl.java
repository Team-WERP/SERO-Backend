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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDailyResponseDTO> getDailyWorkOrders(String date) {
        List<WorkOrderDailyFlatDTO> rows =
                woQueryMapper.selectDailyWorkOrders(date);

        Map<Integer, WorkOrderDailyResponseDTO> lineMap = new LinkedHashMap<>();

        for (WorkOrderDailyFlatDTO row : rows) {

            // Line
            WorkOrderDailyResponseDTO lineDto =
                    lineMap.computeIfAbsent(
                            row.getLineId(),
                            k -> WorkOrderDailyResponseDTO.from(row)
                    );

            // WorkOrder
            WorkOrderSummaryDTO woDto =
                    lineDto.getOrCreateWorkOrder(row);

            // WorkOrderItem
            if (row.getWorkOrderItemId() != null) {
                woDto.addItem(WorkOrderItemDTO.from(row));
            }
        }

        return new ArrayList<>(lineMap.values());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WOEmergencyPRItemResponseDTO> getEmergencyTargets(int lineId) {
        return woQueryMapper.selectEmergencyTargetsByLine(lineId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderResultListResponseDTO> getWorkOrderResultList(
            String startDate,
            String endDate,
            Integer lineId,
            String keyword
    ) {
        return woQueryMapper.selectWorkOrderResultList(
                startDate,
                endDate,
                lineId,
                keyword
        );
    }



}
