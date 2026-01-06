package com.werp.sero.notice.query.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notice.command.domain.aggregate.enums.SearchType;
import com.werp.sero.notice.query.dto.NoticeDetailResponseDTO;
import com.werp.sero.notice.query.dto.NoticeListResponseDTO;
import com.werp.sero.security.principal.CustomUserDetails;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeQueryService {
    NoticeDetailResponseDTO getNoticeDetailInfo(final CustomUserDetails customUserDetails, final int noticeId);

    NoticeListResponseDTO getNotices(final Employee employee, final String category, final String keyword,
                                     final List<SearchType> searchTypes, final boolean onlyMine, final Pageable pageable);
}