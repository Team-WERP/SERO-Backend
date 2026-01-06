package com.werp.sero.notice.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeSummaryResponseDTO {
    private int noticeId;
    private String title;
    private String category;
    private String createdAt;
    private int employeeId;
    private String employeeName;
    private String employeeRankCode;
    private String employeePositionCode;
    private String employeeDepartmentName;
    private Boolean isNoticeAttachment;
    private String pinnedStartAt;
    private String pinnedEndAt;
    private boolean isEmergency;
}