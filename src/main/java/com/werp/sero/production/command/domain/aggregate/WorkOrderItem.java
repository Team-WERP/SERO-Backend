package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.common.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_order_item")
@Getter
@NoArgsConstructor
public class WorkOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pp_id", nullable = false)
    private ProductionPlan productionPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pr_item_id", nullable = false)
    private ProductionRequestItem productionRequestItem;

    @Column(name = "planned_quantity", nullable = false)
    private int plannedQuantity;

    @Column(name = "produced_quantity", nullable = false)
    private int producedQuantity;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    public WorkOrderItem(
            WorkOrder workOrder,
            ProductionPlan productionPlan,
            ProductionRequestItem prItem,
            int plannedQuantity
    ) {
        this.workOrder = workOrder;
        this.productionPlan = productionPlan;
        this.productionRequestItem = prItem;
        this.plannedQuantity = plannedQuantity;
        this.producedQuantity = 0;
        this.status = "WOI_READY";
        this.createdAt = DateTimeUtils.nowDateTime();
    }

    public void complete() {
        this.status = "WOI_DONE";
        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    public void addProducedQuantity(int qty) {
        this.producedQuantity += qty;
    }

}

