package com.werp.sero.material.entity;

import com.werp.sero.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "material")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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

    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Bom> bomList = new ArrayList<>();

    // 수정 메서드
    public void update(String name, String spec, String operationUnit, String baseUnit,
                       Integer moq, Integer cycleTime, Long unitPrice, String imageUrl,
                       Integer conversionRate, Integer safetyStock, String status) {
        this.name = name;
        this.spec = spec;
        this.operationUnit = operationUnit;
        this.baseUnit = baseUnit;
        this.moq = moq != null ? moq : 0;
        this.cycleTime = cycleTime != null ? cycleTime : 0;
        this.unitPrice = unitPrice != null ? unitPrice : 0;
        this.imageUrl = imageUrl;
        this.conversionRate = conversionRate != null ? conversionRate : 0;
        this.safetyStock = safetyStock != null ? safetyStock : 1;
        this.status = status;
        this.updatedAt = getCurrentTimestamp();
    }

    // 비활성화
    public void deactivate() {
        this.status = "MAT_STOP";
        this.updatedAt = getCurrentTimestamp();
    }

    // BOM 추가
    public void addBom(Bom bom) {
        this.bomList.add(bom);
        this.rawMaterialCount = this.bomList.size();
        this.updatedAt = getCurrentTimestamp();
    }

    // BOM 목록 교체
    public void replaceBomList(List<Bom> newBomList) {
        this.bomList.clear();
        this.bomList.addAll(newBomList);
        this.rawMaterialCount = this.bomList.size();
        this.updatedAt = getCurrentTimestamp();
    }

    // 현재 시간 포맷
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}