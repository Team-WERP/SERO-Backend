package com.werp.sero.approval.entity;

import com.werp.sero.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "approval")
@NoArgsConstructor
@Entity
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "approval_code", nullable = false, unique = true)
    private String approvalCode;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'AS_ING'")
    private String status;

    @Column(name = "completed_at")
    private String completedAt;

    @Column(name = "total_line", nullable = false, columnDefinition = "int default 0")
    private int totalLine;

    @Column(name = "ref_code", nullable = false)
    private String refCode;

    @Column(name = "drafted_at", nullable = false)
    private String draftedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drafter_id", nullable = false)
    private Employee employee;
}