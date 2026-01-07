package com.werp.sero.notice.query.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notice.query.dto.NoticeDetailResponseDTO;
import com.werp.sero.notice.query.dto.NoticeListResponseDTO;
import com.werp.sero.security.principal.CustomUserDetails;
import org.springframework.data.domain.Pageable;

public interface NoticeQueryService {
    NoticeDetailResponseDTO getNoticeDetailInfo(final CustomUserDetails customUserDetails, final int noticeId);

    NoticeListResponseDTO getNotices(final Employee employee, final String category, final String keyword,
                                     final boolean onlyMine, final Pageable pageable);

    NoticeListResponseDTO getNoticesByClientEmployee(final String keyword, final Pageable pageable);
}