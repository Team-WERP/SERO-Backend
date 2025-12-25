package com.werp.sero.approval.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "approval_template_line")
@NoArgsConstructor
@Entity
public class ApprovalTemplateLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer sequence;

    @Column(name = "line_type", nullable = false)
    private String lineType;

    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private ApprovalTemplate approvalTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private Employee employee;

    public ApprovalTemplateLine(final Integer sequence, final String lineType, final String note,
                                final ApprovalTemplate approvalTemplate, Employee employee) {
        this.sequence = sequence;
        this.lineType = lineType;
        this.note = note;
        this.approvalTemplate = approvalTemplate;
        this.employee = employee;
    }
}