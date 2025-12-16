package com.werp.sero.production.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "work_order_result")
@NoArgsConstructor
@Entity
public class WorkOrderResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "good_quantity", nullable = false, columnDefinition = "int default 0")
    private int goodQuantity;

    @Column(name = "defective_quantity", nullable = false, columnDefinition = "int default 0")
    private int defectiveQuantity;

    @Column(name = "start_time")
    private String startDate;

    @Column(name = "end_time")
    private String endDate;

    @Column(name = "work_time", nullable = false, columnDefinition = "int default 0")
    private int workTime;

    private String note;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int headCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wo_id", nullable = false)
    private WorkOrder workOrder;
}