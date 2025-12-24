package com.werp.sero.approval.command.application.controller;

import com.werp.sero.approval.command.application.dto.ApprovalTemplateCreateRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalTemplateResponseDTO;
import com.werp.sero.approval.command.application.service.ApprovalTemplateService;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결재선 템플릿 - Command", description = "결재선 템플릿 관련 API")
@RequestMapping("/approval-templates")
@RequiredArgsConstructor
@RestController
public class ApprovalTemplateController {
    private final ApprovalTemplateService approvalTemplateService;

    @Operation(summary = "결재선 템플릿 등록")
    @PostMapping
    public ResponseEntity<ApprovalTemplateResponseDTO> registerApprovalTemplate(@CurrentUser final Employee employee,
                                                                                @Valid @RequestBody ApprovalTemplateCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(approvalTemplateService.registerApprovalTemplate(employee, requestDTO));
    }
}