package com.werp.sero.notice.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "notice_attachment")
@NoArgsConstructor
@Entity
public class NoticeAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    public NoticeAttachment(final String name, final String url, final Notice notice) {
        this.name = name;
        this.url = url;
        this.notice = notice;
    }
}