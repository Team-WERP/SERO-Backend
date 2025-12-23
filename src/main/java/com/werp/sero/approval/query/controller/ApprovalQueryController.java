package com.werp.sero.approval.query.controller;

import com.werp.sero.approval.query.dto.ApprovalFilterRequestDTO;
import com.werp.sero.approval.query.dto.ApprovalListResponseDTO;
import com.werp.sero.approval.query.service.ApprovalQueryService;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결재 - Query", description = "결재 관련 조회 API")
@RequestMapping("/approvals")
@RequiredArgsConstructor
@RestController
public class ApprovalQueryController {
    private final ApprovalQueryService approvalQueryService;

    @Operation(summary = "기안함 목록 조회")
    @GetMapping("/submitted")
    public ResponseEntity<ApprovalListResponseDTO> getSubmittedApprovals(@CurrentUser Employee employee,
                                                                         @ModelAttribute ApprovalFilterRequestDTO filterDTO,
                                                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(approvalQueryService.getSubmittedApprovals(employee, filterDTO, pageable));
    }

    @Operation(summary = "결재함 목록 조회")
    @GetMapping("/archived")
    public ResponseEntity<ApprovalListResponseDTO> getArchivedApprovals(@CurrentUser final Employee employee,
                                                                                 @ModelAttribute ApprovalFilterRequestDTO filterDTO,
                                                                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(approvalQueryService.getArchivedApprovals(employee, filterDTO, pageable));
    }

    @Operation(summary = "결재 요청함 목록 조회")
    @GetMapping("/received")
    public ResponseEntity<ApprovalListResponseDTO> getReceivedApprovals(@CurrentUser final Employee employee,
                                                                                 @ModelAttribute ApprovalFilterRequestDTO filterDTO,
                                                                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(approvalQueryService.getReceivedApprovals(employee, filterDTO, pageable));
    }

    @Operation(summary = "수신/참조함 목록 조회")
    @GetMapping("/referenced")
    public ResponseEntity<ApprovalListResponseDTO> getReferencedApprovals(@CurrentUser final Employee employee,
                                                                        @ModelAttribute ApprovalFilterRequestDTO filterDTO,
                                                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(approvalQueryService.getReferencedApprovals(employee, filterDTO, pageable));
    }
}
