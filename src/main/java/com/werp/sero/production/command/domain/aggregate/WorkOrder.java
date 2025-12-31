package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.exception.InvalidWorkOrderStatusException;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", nullable = false)
    private ProductionLine productionLine;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "wo_url", columnDefinition = "text")
    private String woUrl;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Employee creator;

    public WorkOrder(
            String woCode,
            String workDate,
            ProductionLine productionLine,
            Employee manager,
            Employee creator
    ) {
        this.woCode = woCode;
        this.workDate = workDate;
        this.productionLine = productionLine;
        this.manager = manager;
        this.creator = creator;
        this.status = "WO_READY";
        this.createdAt = DateTimeUtils.nowDateTime();
    }

    public void recalculateQuantity(int totalQuantity) {
        this.quantity = totalQuantity;
    }

    public void start() {
        if (!"WO_READY".equals(this.status)) {
            throw new InvalidWorkOrderStatusException(this.status, "WO_READY");
        }
        this.status = "WO_RUN";
    }

    public void pause() {
        if (!"WO_RUN".equals(this.status)) {
            throw new InvalidWorkOrderStatusException(this.status, "WO_RUN");
        }
        this.status = "WO_PAUSE";
    }

    public void resume() {
        if (!"WO_PAUSE".equals(this.status)) {
            throw new InvalidWorkOrderStatusException(this.status, "WO_PAUSE");
        }
        this.status = "WO_RUN";
    }

    public void end() {
        if (!("WO_RUN".equals(this.status) || "WO_PAUSE".equals(this.status))) {
            throw new InvalidWorkOrderStatusException(this.status, "WO_RUN or WO_PAUSE");
        }
        this.status = "WO_DONE";
    }
}