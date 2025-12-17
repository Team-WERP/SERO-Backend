package com.werp.sero.warehouse.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "warehouse")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    @Column(columnDefinition = "decimal(10,7)")
    private Double latitude;

    @Column(columnDefinition = "decimal(10,7)")
    private Double longitude;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'WHS_WH'")
    private String type;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee employee;
}