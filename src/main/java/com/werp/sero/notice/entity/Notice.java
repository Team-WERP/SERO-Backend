package com.werp.sero.notice.entity;

import com.werp.sero.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "notice")
@NoArgsConstructor
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "attachment_url", columnDefinition = "text")
    private String attachmentUrl;

    @Column(nullable = false)
    private String category;

    @Column(name = "pinned_start_at")
    private String pinnedStartAt;

    @Column(name = "pinned_end_at")
    private String pinnedEndAt;

    @Column(name = "is_emergency", nullable = false, columnDefinition = "tinyint default 0")
    private boolean isEmergency;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Employee employee;
}