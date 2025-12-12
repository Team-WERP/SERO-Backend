package com.werp.sero.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "sales_order_status_history")
@NoArgsConstructor
@Entity
public class SalesOrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String status;

    @Column(name = "so_id", nullable = false)
    private int soId;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "creator_id", nullable = false)
    private int creatorId;
}