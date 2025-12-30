package com.werp.sero.production.command.application.service;

import com.werp.sero.production.command.application.dto.WorkOrderItemPreviewDTO;
import com.werp.sero.production.command.domain.aggregate.WorkOrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkOrderItemDistributor {

    public List<WorkOrderItemPreviewDTO> distribute(
            List<WorkOrderItem> items,
            int goodQuantity
    ) {
        int totalPlanned = items.stream()
                .mapToInt(WorkOrderItem::getPlannedQuantity)
                .sum();

        int remaining = goodQuantity;
        List<WorkOrderItemPreviewDTO> result = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            WorkOrderItem item = items.get(i);

            int qty;
            if (i == items.size() - 1) {
                qty = remaining;
            } else {
                qty = (int) Math.floor(
                        (double) item.getPlannedQuantity() / totalPlanned
                                * goodQuantity
                );
                remaining -= qty;
            }

            result.add(new WorkOrderItemPreviewDTO(
                    item.getId(),
                    item.getProductionRequestItem().getSalesOrderItem().getItemName(),
                    item.getPlannedQuantity(),
                    qty
            ));
        }

        return result;
    }
}
