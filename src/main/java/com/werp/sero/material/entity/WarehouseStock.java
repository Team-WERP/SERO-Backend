package com.werp.sero.material.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "warehouse_stock")
@NoArgsConstructor
@Entity
public class WarehouseStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "safety_stock", nullable = false)
    private int safetyStock;

    @Column(name = "current_stock", nullable = false, columnDefinition = "int default 1")
    private int currentStock;

    @Column(name = "available_stock", nullable = false)
    private int availableStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}