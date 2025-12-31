package com.werp.sero.approval.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalListResponseDTO {
    private List<ApprovalSummaryResponseDTO> approvals;

    private int totalPages;

    private long totalElements;

    private int size;

    private int number;
}