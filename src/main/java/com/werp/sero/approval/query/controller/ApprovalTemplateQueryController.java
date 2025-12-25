package com.werp.sero.approval.query.controller;

import com.werp.sero.approval.query.dto.ApprovalTemplateInfoResponseDTO;
import com.werp.sero.approval.query.service.ApprovalTemplateQueryService;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "결재선 템플릿 - Query", description = "결재선 템플릿 관련 조회 API")
@RequestMapping("/approval-templates")
@RequiredArgsConstructor
@RestController
public class ApprovalTemplateQueryController {
    private final ApprovalTemplateQueryService approvalTemplateQueryService;

    @Operation(summary = "결재선 템플릿 목록 조회")
    @GetMapping
    public ResponseEntity<List<ApprovalTemplateInfoResponseDTO>> getApprovalTemplates(@CurrentUser Employee employee) {
        return ResponseEntity.ok(approvalTemplateQueryService.getApprovalTemplates(employee));
    }

    @Operation(summary = "결재선 템플릿 상세 조회")
    @GetMapping("/{approvalTemplateId}")
    public ResponseEntity<ApprovalTemplateInfoResponseDTO> getApprovalTemplateById(@CurrentUser final Employee employee,
                                                                                     @PathVariable(name = "approvalTemplateId") final int approvalTemplateId) {
        return ResponseEntity.ok(approvalTemplateQueryService.getApprovalTemplateById(employee, approvalTemplateId));
    }
}