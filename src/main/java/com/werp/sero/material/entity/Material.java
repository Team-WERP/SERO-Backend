package com.werp.sero.material.entity;

import com.werp.sero.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "material")
@NoArgsConstructor
@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "material_code", nullable = false, unique = true)
    private String materialCode;

    @Column(nullable = false)
    private String spec;

    @Column(name = "operation_unit")
    private String operationUnit;

    @Column(name = "base_unit", nullable = false)
    private String baseUnit;

    private int moq;

    @Column(name = "cycle_time")
    private int cycleTime;

    @Column(name = "unit_price")
    private long unitPrice;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;

    @Column(name = "conversion_rate")
    private int conversionRate;

    @Column(name = "safety_stock", nullable = false, columnDefinition = "int default 1")
    private int safetyStock;

    @Column(name = "raw_material_count")
    private int rawMaterialCount;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'MAT_NORMAL'")
    private String status;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee employee;
}