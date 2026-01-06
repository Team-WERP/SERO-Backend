package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.material.command.domain.aggregate.Material;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "work_order_material",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_wom_unique",
                        columnNames = {"work_order_id", "raw_material_id"}
                )
        }
)
public class WorkOrderMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_material_id", nullable = false)
    private Material rawMaterial;

    @Column(name = "planned_quantity", nullable = false)
    private int plannedQuantity;

    @Column(name = "actual_quantity", nullable = false)
    private int actualQuantity;

    @Column(nullable = false, length = 30)
    private String status; // WOM_PLANNED, WOM_CONSUMED

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    private WorkOrderMaterial(
            WorkOrder workOrder,
            Material rawMaterial,
            int plannedQuantity,
            String createdAt
    ) {
        this.workOrder = workOrder;
        this.rawMaterial = rawMaterial;
        this.plannedQuantity = plannedQuantity;
        this.actualQuantity = 0;
        this.status = "WOM_PLANNED";
        this.createdAt = createdAt;
    }

    public static WorkOrderMaterial create(
            WorkOrder workOrder,
            Material rawMaterial,
            int plannedQuantity,
            String createdAt
    ) {
        return new WorkOrderMaterial(
                workOrder,
                rawMaterial,
                plannedQuantity,
                createdAt
        );
    }

    public void consume(int actualQuantity, String updatedAt) {
        this.actualQuantity = actualQuantity;
        this.status = "WOM_CONSUMED";
        this.updatedAt = updatedAt;
    }

    public void addPlannedQuantity(int qty) {
        this.plannedQuantity += qty;
    }

    public void touch(String now) {
        this.updatedAt = now;
    }

}
