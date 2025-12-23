package com.werp.sero.approval.command.application.controller;

import com.werp.sero.approval.command.application.dto.ApprovalCreateRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalDecisionRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalResponseDTO;
import com.werp.sero.approval.command.application.service.ApprovalCommandService;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "결재 - Command", description = "결재 관련 API")
@RequestMapping("/approvals")
@RequiredArgsConstructor
@RestController
public class ApprovalCommandController {
    private final ApprovalCommandService approvalCommandService;

    @Operation(summary = "결재 상신")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApprovalResponseDTO> submitForApproval(@CurrentUser final Employee employee,
                                                                 @Valid @RequestPart(name = "requestDTO") final ApprovalCreateRequestDTO requestDTO,
                                                                 @RequestPart(name = "files", required = false) final List<MultipartFile> files) {
        return ResponseEntity.ok(approvalCommandService.submitForApproval(employee, requestDTO, files));
    }

    @Operation(summary = "결재 승인")
    @PostMapping("/{approvalId}/approve")
    public ResponseEntity<Void> approve(@CurrentUser final Employee employee,
                                        @PathVariable("approvalId") final int approvalId,
                                        @RequestBody final ApprovalDecisionRequestDTO requestDTO) {
        approvalCommandService.approve(employee, approvalId, requestDTO);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "결재 반려")
    @PostMapping("/{approvalId}/reject")
    public ResponseEntity<Void> reject(@CurrentUser final Employee employee,
                                       @PathVariable("approvalId") final int approvalId,
                                       @RequestBody final ApprovalDecisionRequestDTO requestDTO) {
        approvalCommandService.reject(employee, approvalId,requestDTO);

        return ResponseEntity.ok().build();
    }
}