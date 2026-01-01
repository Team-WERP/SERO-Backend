package com.werp.sero.order.command.application.service;

import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.query.dao.SOMapper;
import com.werp.sero.order.query.dto.SOItemsHistoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SOStateService {

    private final SORepository salesOrderRepository;
    private final SOMapper soMapper;

    @Transactional
    public void updateOrderStateByHistory(int orderId) {
        SalesOrder order = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        SOItemsHistoryResponseDTO history = soMapper.selectOrderItemHistory(orderId, null, true);

        String newState = calculateState(history.getItems());

        order.changeState(newState);
    }


    private String calculateState(List<SOItemsHistoryResponseDTO.SOItemHistoryDTO> items) {
        if (items == null || items.isEmpty()) return "ORD_APPR_DONE";

        int totalItemCount = items.size();
        int completedCount = 0;

        boolean hasPartial = false;
        boolean hasPro = false;
        boolean hasShipping = false;

        for (SOItemsHistoryResponseDTO.SOItemHistoryDTO item : items) {
            int pr = item.getPrQuantity();
            int pi = item.getPiQuantity();
            int doc = item.getDoQuantity();
            int comp = item.getCompletedQuantity();
            int orderedQty = item.getItem().getQuantity();

            if (orderedQty <= comp) {
                completedCount++;
            }
            else if (pr > pi && doc != 0) {
                hasPartial = true;
            }
            else if (pr > pi && doc == 0) {
                hasPro = true;
            }
            else if (((pr > 0 && pr == pi) || (pr == 0 && pi == 0)) && orderedQty > comp) {
                hasShipping = true;
            }
        }

        if (completedCount == totalItemCount) {
            return "ORD_DONE";
        }

        if (hasPartial || (hasPro && hasShipping)) {
            return "ORD_PARTIAL_ING";
        }

        if (hasPro) {
            return "ORD_PRO";
        }

        if (hasShipping) {
            return "ORD_SHIPPING";
        }

        return "ORD_APPR_DONE";
    }
}