package com.werp.sero.outbound.entity;

import com.werp.sero.approval.entity.Approval;
import com.werp.sero.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "delivery_order")
@NoArgsConstructor
@Entity
public class DeliveryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "do_code", nullable = false, unique = true)
    private String doCode;

    @Column(name = "do_url", nullable = false, columnDefinition = "text")
    private String doUrl;

    private String note;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private Approval approval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee employee;
}