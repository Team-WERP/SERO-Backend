package com.werp.sero.order.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "sales_order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class SalesOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "item_code", nullable = false)
    private String itemCode;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String spec;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @Column(nullable = false)
    private String unit;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "total_price", nullable = false, columnDefinition = "bigint default 0")
    private long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;
}