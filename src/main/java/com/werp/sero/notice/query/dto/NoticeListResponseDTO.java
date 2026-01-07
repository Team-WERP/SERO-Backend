package com.werp.sero.notice.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeListResponseDTO {
    private List<NoticeSummaryResponseDTO> notices;

    private int totalPages;

    private long totalElements;

    private int size;

    private int number;
}