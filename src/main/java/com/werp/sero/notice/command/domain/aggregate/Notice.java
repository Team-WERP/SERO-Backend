package com.werp.sero.notice.command.domain.aggregate;

import com.werp.sero.employee.command.domain.aggregate.Employee;
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

    public Notice(final String title, final String content, final String category, final String pinnedStartAt,
                  final String pinnedEndAt, final boolean isEmergency, final String createdAt, final Employee employee) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.pinnedStartAt = pinnedStartAt;
        this.pinnedEndAt = pinnedEndAt;
        this.isEmergency = isEmergency;
        this.createdAt = createdAt;
        this.employee = employee;
    }
}