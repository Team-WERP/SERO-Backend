package com.werp.sero.shipping.command.domain.aggregate;

import com.werp.sero.approval.command.domain.aggregate.Approval;
import com.werp.sero.employee.command.domain.aggregate.Employee;
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