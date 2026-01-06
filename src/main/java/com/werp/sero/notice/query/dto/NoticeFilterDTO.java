package com.werp.sero.notice.query.dto;

import com.werp.sero.notice.command.domain.aggregate.enums.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeFilterDTO {
    private int employeeId;
    private String category;
    private String keyword;
    private List<SearchType> searchTypes;
    private boolean onlyMine;
    private int limit;
    private long offset;
}