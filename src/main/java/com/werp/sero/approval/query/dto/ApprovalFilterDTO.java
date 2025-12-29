package com.werp.sero.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalFilterDTO {
    @Schema(description = "로그인한 회원의 ID(PK)")
    private int employeeId;

    @Schema(description = "결재 상태 ex) 진행 중, (최종) 승인, 반려")
    private String approvalStatus;

    @Schema(description = "결재 타입 ex) 결재, 협조, 수신, 참조")
    private List<String> approvalTypeList;

    @Schema(description = "검색 키워드")
    private String keyword;

    @Schema(description = "조회 시작일")
    private LocalDate startDate;

    @Schema(description = "조회 종료일")
    private LocalDate endDate;

    @Schema(description = "문서 타입")
    private String refDocType;

    @Schema(description = "결재선 상태 ex) 대기, 검토, 승인, 거절")
    private List<String> approvalLineStatusList;

    @Schema(description = "열람 여부")
    private Boolean isRead;

    private int limit;

    private long offset;
}