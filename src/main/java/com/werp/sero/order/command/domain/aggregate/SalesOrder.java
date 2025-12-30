package com.werp.sero.order.command.domain.aggregate;

import com.werp.sero.client.command.domain.aggregate.Client;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "sales_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "so_code", nullable = false, unique = true)
    private String soCode;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "ordered_at", nullable = false)
    private String orderedAt;

    @Column(name = "shipped_at", nullable = false)
    private String shippedAt;

    @Column(name = "po_code")
    private String poCode;

    @Column(name = "total_quantity", nullable = false, columnDefinition = "int default 0")
    private int totalQuantity;

    @Column(name = "total_item_count", nullable = false, columnDefinition = "int default 0")
    private int totalItemCount;

    @Column(name = "total_price", nullable = false, columnDefinition = "bigint default 0")
    private long totalPrice;

    @Column(name = "so_url", columnDefinition = "text")
    private String soUrl;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "main_item_name", nullable = false)
    private String mainItemName;

    private String note;

    @Column(name = "approval_code", unique = true)
    private String approvalCode;

    @Column(name = "shipping_name", nullable = false)
    private String shippingName;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "decimal(10,7)")
    private Double latitude;

    @Column(columnDefinition = "decimal(10,7)")
    private Double longitude;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_contact", nullable = false)
    private String recipientContact;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'ORD_RED'")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_manager_id", nullable = false)
    private ClientEmployee clientEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee employee;

    public void cancel(String reason) {
        final String CANCEL_STATUS = "ORD_CANCEL";

        this.status = CANCEL_STATUS;
        this.rejectionReason = reason;
    }

    public void updateManager(Employee manager) {
        final String UPDATE_STATUS = "ORD_RVW";

        this.employee = manager;
        this.status = UPDATE_STATUS;
    }

    public void updateApprovalInfo(final String approvalCode, final String status) {
        this.approvalCode = approvalCode;
        this.status = status;
    }

    public void updateSoUrl(String pdfUrl) {
        this.soUrl = pdfUrl;
    }

    public void completeOrder() {
        final String COMPLETE_STATUS = "ORD_DONE";
        this.status = COMPLETE_STATUS;
    }
}