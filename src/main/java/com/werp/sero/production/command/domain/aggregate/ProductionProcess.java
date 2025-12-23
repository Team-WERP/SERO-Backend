package com.werp.sero.production.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "production_process")
@NoArgsConstructor
@Entity
public class ProductionProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "process_name", nullable = false)
    private String processName;

    @Column(name = "process_order", nullable = false)
    private String processOrder;

    @Column(name = "headcount", nullable = false, columnDefinition = "int default 1")
    private int headCount;

    @Column(name = "standard_time", nullable = false, columnDefinition = "int default 0")
    private int standardTime;

    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_material_id", nullable = false)
    private LineMaterial lineMaterial;
}