package com.werp.sero.notice.query.dao;

import com.werp.sero.notice.query.dto.NoticeDetailResponseDTO;
import com.werp.sero.notice.query.dto.NoticeFilterDTO;
import com.werp.sero.notice.query.dto.NoticeSummaryResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    NoticeDetailResponseDTO findByNoticeId(final int noticeId);

    long countNoticesByFilter(final NoticeFilterDTO filter);

    List<NoticeSummaryResponseDTO> findNoticesByFilter(final NoticeFilterDTO filter);
}