package com.werp.sero.notice.command.domain.repository;

import com.werp.sero.notice.command.domain.aggregate.NoticeAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachment, Integer> {
    void deleteByNoticeId(final int noticeId);

    List<NoticeAttachment> findByNoticeId(final int noticeId);
}