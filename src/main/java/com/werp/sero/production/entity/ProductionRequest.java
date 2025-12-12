package com.werp.sero.production.entity;

import com.werp.sero.employee.entity.Employee;
import com.werp.sero.order.entity.SalesOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "sales_order")
@NoArgsConstructor
@Entity
public class ProductionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pr_code", nullable = false)
    private String prCode;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'PR_RVW'")
    private String status;

    @Column(name = "requested_at", nullable = false)
    private String requestedAt;

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
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee employee;
}