package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "production_request_item")
@NoArgsConstructor
@Entity
public class ProductionRequestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantity;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pr_id", nullable = false)
    private ProductionRequest productionRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_item_id", nullable = false)
    private SalesOrderItem salesOrderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee employee;

    public static ProductionRequestItem createDraft(
            ProductionRequest pr,
            SalesOrderItem soItem,
            int quantity
    ) {
        ProductionRequestItem prItem = new ProductionRequestItem();
        prItem.productionRequest = pr;
        prItem.salesOrderItem = soItem;
        prItem.quantity = quantity;
        prItem.status = "PIS_TMP";
        return prItem;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}