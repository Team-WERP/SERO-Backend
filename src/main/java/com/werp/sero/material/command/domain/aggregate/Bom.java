package com.werp.sero.material.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "bom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_material_id", nullable = false)
    private Material rawMaterial;

    private Bom(Material material, Material rawMaterial, int requirement, String note, String createdAt) {
        this.material = material;
        this.rawMaterial = rawMaterial;
        this.requirement = requirement;
        this.note = note;
        this.createdAt = createdAt;
    }

    public static Bom create(Material material, Material rawMaterial,
                             int requirement, String note, String createdAt) {
        return new Bom(material, rawMaterial, requirement, note, createdAt);
    }
}