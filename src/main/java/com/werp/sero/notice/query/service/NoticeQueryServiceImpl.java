package com.werp.sero.notice.query.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notice.exception.NoticeNotFoundException;
import com.werp.sero.notice.query.dao.NoticeMapper;
import com.werp.sero.notice.query.dto.NoticeDetailResponseDTO;
import com.werp.sero.notice.query.dto.NoticeFilterDTO;
import com.werp.sero.notice.query.dto.NoticeListResponseDTO;
import com.werp.sero.notice.query.dto.NoticeSummaryResponseDTO;
import com.werp.sero.security.enums.Type;
import com.werp.sero.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeQueryServiceImpl implements NoticeQueryService {
    private static final String NOTICE_CUSTOMER = "NOTICE_CUSTOMER";

    private final NoticeMapper noticeMapper;

    @Transactional(readOnly = true)
    @Override
    public NoticeDetailResponseDTO getNoticeDetailInfo(final CustomUserDetails customUserDetails, final int noticeId) {
        final NoticeDetailResponseDTO responseDTO = noticeMapper.findByNoticeId(noticeId);

        if (responseDTO == null) {
            throw new NoticeNotFoundException();
        }

        if (Type.CLIENT_EMPLOYEE.equals(customUserDetails.getType()) && !NOTICE_CUSTOMER.equals(responseDTO.getCategory())) {
            throw new NoticeNotFoundException();
        }

        return responseDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public NoticeListResponseDTO getNotices(final Employee employee, final String category, final String keyword,
                                            final boolean onlyMine, final Pageable pageable) {
        final NoticeFilterDTO filter = NoticeFilterDTO.builder()
                .category(category)
                .keyword(keyword)
                .employeeId(employee.getId())
                .onlyMine(onlyMine)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = noticeMapper.countNoticesByFilter(filter);

        final List<NoticeSummaryResponseDTO> notices = noticeMapper.findNoticesByFilter(filter);

        final int totalPages = (int) ((totalElements + pageable.getPageSize() - 1) / pageable.getPageSize());

        return NoticeListResponseDTO.builder()
                .notices(notices)
                .totalPages(totalPages)
                .size(pageable.getPageSize())
                .number(pageable.getPageNumber())
                .totalElements(totalElements)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public NoticeListResponseDTO getNoticesByClientEmployee(final String keyword, final Pageable pageable) {
        final NoticeFilterDTO filter = NoticeFilterDTO.builder()
                .category(NOTICE_CUSTOMER)
                .keyword(keyword)
                .employeeId(null)
                .onlyMine(false)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = noticeMapper.countNoticesByFilter(filter);

        final List<NoticeSummaryResponseDTO> notices = noticeMapper.findNoticesByFilter(filter);

        final int totalPages = (int) ((totalElements + pageable.getPageSize() - 1) / pageable.getPageSize());

        return NoticeListResponseDTO.builder()
                .notices(notices)
                .totalPages(totalPages)
                .size(pageable.getPageSize())
                .number(pageable.getPageNumber())
                .totalElements(totalElements)
                .build();
    }
}