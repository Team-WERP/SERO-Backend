package com.werp.sero.approval.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "approval_attachment")
@NoArgsConstructor
@Entity
public class ApprovalAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_id", nullable = false)
    private Approval approval;
}