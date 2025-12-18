package com.werp.sero.deadline.command.domain.aggregate;

import com.werp.sero.warehouse.command.domain.aggregate.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "production_line")
@NoArgsConstructor
@Entity
public class ProductionLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "line_name", nullable = false, unique = true)
    private String lineName;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'PL_ACTIVE'")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factory_id")
    private Warehouse factory;
}