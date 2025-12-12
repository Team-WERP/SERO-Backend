package com.werp.sero.approval.entity;

import com.werp.sero.employee.entity.Employee;
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

    @Column(nullable = false)
    private int sequence;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'ALS_PEND'")
    private String status;

    @Column(name = "viewed_at")
    private String viewedAt;

    @Column(name = "processed_at")
    private String processedAt;

    private String note;

    @Column(name = "signature_url", columnDefinition = "text")
    private String signatureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_id", nullable = false)
    private Approval approval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private Employee employee;
}