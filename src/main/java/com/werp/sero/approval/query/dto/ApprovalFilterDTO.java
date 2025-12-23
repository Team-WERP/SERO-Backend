package com.werp.sero.approval.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalFilterDTO {
    private String approvalRole;

    private int employeeId;

    private String approvalStatus;

    private List<String> approvalLineStatusList;

    private String keyword;

    private LocalDate startDate;

    private LocalDate endDate;

    private String refType;

    private Boolean isRead;

    private String approvalLineType;

    private int limit;

    private long offset;
}