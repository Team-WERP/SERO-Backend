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
public class NoticeFilterDTO {
    private Integer employeeId;
    private String category;
    private String keyword;
    private boolean onlyMine;
    private int limit;
    private long offset;
}