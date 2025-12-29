package com.werp.sero.production.command.application.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.WorkOrderCreateRequestDTO;
import com.werp.sero.production.command.application.dto.WorkOrderEndRequest;
import com.werp.sero.production.command.application.dto.WorkOrderResultPreviewRequestDTO;
import com.werp.sero.production.command.application.dto.WorkOrderResultPreviewResponseDTO;
import com.werp.sero.production.command.application.service.WOCommandService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
@Tag(name = "작업지시 - Command")
public class WOCommandController {
    private final WOCommandService woCommandService;

    @Operation(
            summary = "생산계획 기반 작업지시 생성",
            description = """
                확정된 생산계획(PP)을 기준으로 작업지시를 생성합니다.
                
                제약:
                - PP 상태는 PP_CONFIRMED 이어야 함
                """
    )
    @PostMapping
    public ResponseEntity<Void> createWorkOrder(
            @RequestBody @Valid WorkOrderCreateRequestDTO request,
            @CurrentUser Employee currentEmployee
    ) {
        woCommandService.createWorkOrder(request, currentEmployee);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작업 시작")
    @PostMapping("/{woId}/start")
    public ResponseEntity<Void> start(
            @PathVariable int woId,
            @RequestParam(required = false) String note
    ) {
        woCommandService.start(woId, note);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작업 일시정지")
    @PostMapping("/{woId}/pause")
    public ResponseEntity<Void> pause(
            @PathVariable int woId,
            @RequestParam(required = false) String note
    ) {
        woCommandService.pause(woId, note);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작업 재개")
    @PostMapping("/{woId}/resume")
    public ResponseEntity<Void> resume(
            @PathVariable int woId,
            @RequestParam(required = false) String note
    ) {
        woCommandService.resume(woId, note);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작업 종료")
    @PostMapping("/{woId}/end")
    public ResponseEntity<Void> end(
            @PathVariable int woId,
            @RequestBody WorkOrderEndRequest request
    ) {
        woCommandService.end(woId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "생산 실적 분배 미리보기")
    @PostMapping("/{woId}/result/preview")
    public WorkOrderResultPreviewResponseDTO preview(
            @PathVariable int woId,
            @RequestBody WorkOrderResultPreviewRequestDTO request
    ) {
        return woCommandService.previewResult(woId, request);
    }
}
