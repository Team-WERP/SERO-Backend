package com.werp.sero.approval.entity;

import com.werp.sero.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "approval_template")
@NoArgsConstructor
@Entity
public class ApprovalTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Employee employee;
}