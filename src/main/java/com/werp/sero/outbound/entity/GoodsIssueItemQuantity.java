package com.werp.sero.outbound.entity;

import com.werp.sero.order.entity.SalesOrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "goods_issue_item_quantity")
@NoArgsConstructor
@Entity
public class GoodsIssueItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_id", nullable = false)
    private GoodsIssue goodsIssue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_item_id", nullable = false)
    private SalesOrderItem salesOrderItem;
}