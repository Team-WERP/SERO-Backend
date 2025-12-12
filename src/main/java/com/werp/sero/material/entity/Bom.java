package com.werp.sero.material.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "bom")
@NoArgsConstructor
@Entity
public class Bom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int requirement;

    private String note;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_material_id")
    private Material rawMaterial;
}