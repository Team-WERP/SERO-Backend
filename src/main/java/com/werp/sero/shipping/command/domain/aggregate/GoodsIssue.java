package com.werp.sero.shipping.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.warehouse.command.domain.aggregate.Warehouse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "goods_issue")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class GoodsIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "gi_code", nullable = false, unique = true)
    private String giCode;

    @Column(name = "approval_code")
    private String approvalCode;

    @Column(name = "gi_url", columnDefinition = "text")
    private String giUrl;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'GI_RVW'")
    private String status;

    private String note;

    @Column(name = "do_code", nullable = false, unique = true)
    private String doCode;

    @Column(name = "created_at")
    private String createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drafter_id", nullable = false)
    private Employee drafter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gi_manager_id", nullable = false)
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    /**
     * 출고 완료 처리
     * 상태를 완료(GI_CMP)로 변경
     */
    public void completeGoodsIssue() {
        if (!"GI_APP".equals(this.status)) {
            throw new IllegalStateException("승인된 출고지시만 완료 처리할 수 있습니다.");
        }
        this.status = "GI_CMP";
    }

    /**
     * 결재 승인 처리
     * approvalCode 설정 및 상태를 승인(GI_APP)으로 변경
     */
    public void approve(String approvalCode) {
        if (!"GI_RVW".equals(this.status)) {
            throw new IllegalStateException("검토 중인 출고지시만 승인할 수 있습니다.");
        }
        this.approvalCode = approvalCode;
        this.status = "GI_APP";
    }
}