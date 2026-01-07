package com.werp.sero.warehouse.command.domain.aggregate;

import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.warehouse.exception.InsufficientStockException;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "warehouse_stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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


    public void allocateStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("할당 수량은 0보다 커야 합니다.");
        }

        if (this.availableStock < quantity) {
            throw new InsufficientStockException(
                String.format("가용 재고가 부족합니다. 필요: %d개, 가용: %d개", quantity, this.availableStock)
            );
        }
    }



    public void deductStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("출고 수량은 0보다 커야 합니다.");
        }

        if (this.currentStock < quantity) {
            throw new InsufficientStockException(
                String.format("현재 재고가 부족합니다. 필요: %d개, 현재: %d개", quantity, this.currentStock)
            );
        }

        this.currentStock -= quantity;
        this.availableStock -= quantity; // 가용 재고도 함께 감소
    }

    public void increaseStock(int quantity) {
        this.currentStock += quantity;
        this.availableStock += quantity; // 가용도 증가
    }


}