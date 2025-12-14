package com.werp.sero.production.entity;

import com.werp.sero.employee.entity.Employee;
import com.werp.sero.order.entity.SalesOrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "production_request_item_quantity")
@NoArgsConstructor
@Entity
public class ProductionRequestItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pr_id", nullable = false)
    private ProductionRequest productionRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_item_id", nullable = false)
    private SalesOrderItem salesOrderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee employee;
}