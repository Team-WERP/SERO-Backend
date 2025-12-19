package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.material.command.application.service.MaterialCommandServiceImpl;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Getter
@Table(name = "production_request")
@NoArgsConstructor
@Entity
public class ProductionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pr_code")
    private String prCode;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'PR_TMP'")
    private String status;

    @Column(name = "production_status")
    private String productionStatus;

    @Column(name = "requested_at")
    private String requestedAt;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "due_at", nullable = false)
    private String dueAt;

    private String reason;

    @Column(name = "pr_url", columnDefinition = "text")
    private String prUrl;

    @Column(name = "total_quantity", nullable = false, columnDefinition = "int default 0")
    private int totalQuantity;

    @Column(name = "approval_code")
    private String approvalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drafter_id", nullable = false)
    private Employee drafter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    public static ProductionRequest createDraft(
            SalesOrder so,
            Employee drafter,
            String dueAt,
            String reason
    ) {
        ProductionRequest pr = new ProductionRequest();
        pr.salesOrder = so;
        pr.drafter = drafter;
        pr.dueAt = dueAt;
        pr.reason = reason;
        pr.status = "PR_TMP";
        pr.totalQuantity = 0;
        pr.createdAt = DateTimeUtils.nowDateTime();
        return pr;
    }

    public ProductionRequestItem addItem(SalesOrderItem soItem, int quantity) {
        ProductionRequestItem item =
                ProductionRequestItem.createDraft(this, soItem, quantity);

        this.totalQuantity += quantity;

        return item;
    }

    public void changeDueAt(String dueAt) { this.dueAt = dueAt; }
    public void changeReason(String reason) { this.reason = reason; }

    public void changeTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

}