package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.production.exception.InvalidProductionPlanStatusException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "production_plan")
@NoArgsConstructor
@Entity
public class ProductionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pp_code", unique = true)
    private String ppCode;

    @Column(name = "status", nullable = false, length = 30)
    private String status; // PP_DRAFT, PP_CONFIRMED, PP_CANCELLED

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "production_quantity", nullable = false)
    private int productionQuantity;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pr_item_id", nullable = false)
    private ProductionRequestItem productionRequestItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_line_id")
    private ProductionLine productionLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    // 생산계획 수립 대상 추가 (DRAFT)
    public static ProductionPlan createDraft(
            ProductionRequestItem prItem,
            Material material,
            Employee employee
    ) {
        ProductionPlan plan = new ProductionPlan();
        plan.productionRequestItem = prItem;
        plan.material = material;
        plan.employee = employee;
        plan.status = "PP_DRAFT";
        plan.productionQuantity = 0;
        plan.createdAt = DateTimeUtils.nowDateTime();
        return plan;
    }

    // 생산계획 확정(DRAFT → CONFIRMED)
    public void confirm(
            ProductionLine productionLine,
            Employee manager,
            String startDate,
            String endDate,
            int productionQuantity,
            String ppCode
    ) {
        if (!"PP_DRAFT".equals(this.status)) {
            throw new InvalidProductionPlanStatusException();
        }
        this.productionLine = productionLine;
        this.employee = manager;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productionQuantity = productionQuantity;
        this.ppCode = ppCode;
        this.status = "PP_CONFIRMED";
        this.updatedAt = DateTimeUtils.nowDateTime();
    }
}