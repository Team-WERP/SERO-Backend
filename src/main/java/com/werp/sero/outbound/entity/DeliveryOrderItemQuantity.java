package com.werp.sero.outbound.entity;

import com.werp.sero.order.entity.SalesOrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "delivery_order_item_quantity")
@NoArgsConstructor
@Entity
public class DeliveryOrderItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "do_quantity", nullable = false, columnDefinition = "int default 0")
    private int doQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "do_id", nullable = false)
    private DeliveryOrder deliveryOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_item_id", nullable = false)
    private SalesOrderItem salesOrderItem;
}