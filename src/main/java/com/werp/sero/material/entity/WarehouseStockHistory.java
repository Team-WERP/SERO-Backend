package com.werp.sero.material.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "warehouse_stock_history")
@NoArgsConstructor
@Entity
public class WarehouseStockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "warehouse_stock_id", nullable = false)
    private int warehouseStockId;

    @Column(nullable = false)
    private String type;

    private String reason;

    @Column(name = "changed_quantity", nullable = false, columnDefinition = "int default 0")
    private int changedQuantity;

    @Column(nullable = false)
    private int currentStock;

    @Column(name = "created_at", nullable = false)
    private String createdAt;
}