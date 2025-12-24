package com.werp.sero.shipping.command.domain.aggregate;

import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "delivery_order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeliveryOrderItem {
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

    @Builder
    private DeliveryOrderItem(int doQuantity, DeliveryOrder deliveryOrder, SalesOrderItem salesOrderItem) {
        this.doQuantity = doQuantity;
        this.deliveryOrder = deliveryOrder;
        this.salesOrderItem = salesOrderItem;
    }
}