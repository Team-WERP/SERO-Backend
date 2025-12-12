package com.werp.sero.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "sales_order_item_history")
@NoArgsConstructor
@Entity
public class SalesOrderItemHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pr_quantity", nullable = false, columnDefinition = "int default 0")
    private int prQuantity;

    @Column(name = "pi_quantity", nullable = false, columnDefinition = "int default 0")
    private int piQuantity;

    @Column(name = "gi_quantity", nullable = false, columnDefinition = "int default 0")
    private int giQuantity;

    @Column(name = "shipped_quantity", nullable = false, columnDefinition = "int default 0")
    private int shippedQuantity;

    @Column(name = "do_quantity", nullable = false, columnDefinition = "int default 0")
    private int doQuantity;

    @Column(name = "completed_quantity", nullable = false, columnDefinition = "int default 0")
    private int completedQuantity;

    @Column(name = "so_item_id", nullable = false)
    private int soItemId;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "creator_id", nullable = false)
    private int creatorId;
}