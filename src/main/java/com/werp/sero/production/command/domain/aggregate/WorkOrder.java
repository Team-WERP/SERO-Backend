package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "work_order")
@NoArgsConstructor
@Entity
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "wo_code", nullable = false, unique = true)
    private String woCode;

    @Column(name = "work_date", nullable = false)
    private String workDate;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "wo_url", columnDefinition = "text")
    private String woUrl;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pr_id", nullable = false)
    private ProductionRequest productionRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pp_id", nullable = false)
    private ProductionPlan productionPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Employee creator;

    public WorkOrder(
            String woCode,
            String workDate,
            int quantity,
            ProductionRequest productionRequest,
            ProductionPlan productionPlan,
            Employee manager,
            Employee creator
    ) {
        this.woCode = woCode;
        this.workDate = workDate;
        this.quantity = quantity;
        this.productionRequest = productionRequest;
        this.productionPlan = productionPlan;
        this.manager = manager;
        this.creator = creator;
        this.createdAt = DateTimeUtils.nowDateTime();
    }
}