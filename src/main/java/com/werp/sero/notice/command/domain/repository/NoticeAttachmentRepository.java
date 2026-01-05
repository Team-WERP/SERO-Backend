package com.werp.sero.notice.command.domain.repository;

import com.werp.sero.notice.command.domain.aggregate.NoticeAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachment, Integer> {
}