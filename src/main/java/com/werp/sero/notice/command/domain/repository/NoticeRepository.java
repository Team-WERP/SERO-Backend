package com.werp.sero.notice.command.domain.repository;

import com.werp.sero.notice.command.domain.aggregate.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}