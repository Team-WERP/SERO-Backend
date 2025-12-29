package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.production.exception.InvalidProducedQuantityException;
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

    @Column(name = "produced_quantity", nullable = false, columnDefinition = "int default 0")
    private int producedQuantity;

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

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public void startProducing() {
        if (!"PIS_PRODUCING".equals(this.status)) {
            this.status = "PIS_PRODUCING";
        }
    }

    public void addProducedQuantity(int qty) {
        if (qty < 0) throw new InvalidProducedQuantityException();
        this.producedQuantity += qty;

        if (this.producedQuantity >= this.quantity) {
            this.status = "PIS_DONE";
        }
    }

}