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
        boolean isPro = false;
        boolean isShipping = false;
        boolean isPartial = false;
        boolean isApprDone = false;
        boolean isShippingDone = false;

        for (SOItemsHistoryResponseDTO.SOItemHistoryDTO item : items) {
            int pr = item.getPrQuantity();
            int pi = item.getPiQuantity();
            int doc = item.getDoQuantity();
            int comp = item.getCompletedQuantity();

            if (pr > pi && doc != 0) {
                isPartial = true;
            }
            else if (pr > pi && doc == 0) {
                isPro = true;
            }
            else if ((pr > 0 && pr == pi && doc > comp) || (pr == 0 && pi == 0 && doc > comp)) {
                isShipping = true;
            }
            else if (pr > 0 && pi == 0 && doc == 0) {
                isApprDone = true;
            }

            else if (doc == comp) {
                isShippingDone = true;
            }
        }

        if (isPartial) return "ORD_PARTIAL_ING";
        if (isPro) return "ORD_PRO";
        if (isShipping) return "ORD_SHIPPING";
        if (isApprDone) return "ORD_APPR_DONE";
        if (isShippingDone) return "ORD_DONE";

        return "ORD_APPR_DONE";
    }
}