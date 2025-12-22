package com.werp.sero.approval.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "approval_line")
@NoArgsConstructor
@Entity
public class ApprovalLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "line_type", nullable = false)
    private String lineType;

    //    @Column(nullable = false)
//    수신 또는 참조인 경우 순서가 필요 없으므로 null 허용
    private Integer sequence;

    private String status;

    @Column(name = "viewed_at")
    private String viewedAt;

    @Column(name = "processed_at")
    private String processedAt;

    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_id", nullable = false)
    private Approval approval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private Employee employee;

    public ApprovalLine(final String lineType, final Integer sequence, final String status, final Approval approval,
                        final Employee employee) {
        this.lineType = lineType;
        this.sequence = sequence;
        this.status = status;
        this.approval = approval;
        this.employee = employee;
    }
}