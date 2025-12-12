package com.werp.sero.production.entity;

import com.werp.sero.material.entity.Material;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "line_material")
@NoArgsConstructor
@Entity
public class LineMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "cycle_time", nullable = false, columnDefinition = "int default 0")
    private int cycleTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private ProductionLine productionLine;
}