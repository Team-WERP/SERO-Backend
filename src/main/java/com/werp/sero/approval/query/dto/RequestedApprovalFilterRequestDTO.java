package com.werp.sero.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestedApprovalFilterRequestDTO extends ApprovalBaseFilterRequestDTO {
    @Schema(description = "읽음 여부")
    private Boolean isRead;

    @Schema(description = "결재 타입")
    private String approvalType;
}