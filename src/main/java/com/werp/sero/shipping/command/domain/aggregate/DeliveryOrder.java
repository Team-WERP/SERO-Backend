package com.werp.sero.shipping.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "delivery_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    @Builder
    private DeliveryOrder(String doCode, String doUrl, String note, String createdAt,
                         SalesOrder salesOrder, Employee manager) {
        this.doCode = doCode;
        this.doUrl = doUrl;
        this.note = note;
        this.createdAt = createdAt;
        this.salesOrder = salesOrder;
        this.manager = manager;
    }
}