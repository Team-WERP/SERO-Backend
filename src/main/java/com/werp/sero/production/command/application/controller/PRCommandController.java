package com.werp.sero.production.command.application.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.PRDraftCreateRequestDTO;
import com.werp.sero.production.command.application.dto.PRDraftUpdateRequestDTO;
import com.werp.sero.production.command.application.dto.PRManagerAssignRequestDTO;
import com.werp.sero.production.command.application.service.PRCommandService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "생산요청 - Command",
        description = "생산요청 임시저장 및 요청 처리 API"
)
@RestController
@RequestMapping("/production-requests")
@RequiredArgsConstructor
public class PRCommandController {
    private final PRCommandService PRCommandService;

    @Operation(
            summary = "생산요청 임시저장 생성",
            description = """
                    수주(SO)를 기준으로 생산요청을 임시저장(PR_TMP) 상태로 생성합니다.
                            
                    - 최초 생성 시 상태는 PR_TMP(임시저장)
                    - 품목 정보는 선택 사항
                    """
    )
    @PostMapping("/draft")
    public ResponseEntity<Integer> createDraft(
            @RequestBody PRDraftCreateRequestDTO dto,
            @CurrentUser Employee currentEmployee
            ) {
        int prId = PRCommandService.createDraft(dto, currentEmployee);
        return ResponseEntity.ok(prId);
    }

    @Operation(
            summary = "임시 저장된 생산요청 수정",
            description = """
                임시 저장(PR_TMP) 상태의 생산요청을 수정합니다.
                
                - 작성자(drafter)만 수정 가능
                - 수정 가능 항목: 납기일(dueAt), 사유(reason), 품목별 생산요청 수량(items)
                """
    )
    @PutMapping("/drafts/{prId}")
    public ResponseEntity<Void> updateDraft(
            @PathVariable int prId,
            @RequestBody PRDraftUpdateRequestDTO dto,
            @CurrentUser Employee currentEmployee
    ) {
        PRCommandService.updateDraft(prId, dto, currentEmployee);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "임시 저장된 생산요청 요청",
            description = """
                임시 저장(PR_TMP) 상태의 생산요청을 실제 생산요청으로 확정합니다.

                - 작성자 본인만 요청 가능
                - 임시 저장 상태(PR_TMP)에서만 요청 가능
                - 생산요청 수량(totalQuantity)이 0 초과여야 합니다.
                """
    )
    @PostMapping("/{prId}/request")
    public ResponseEntity<Void> requestProduction(
            @PathVariable int prId,
            @CurrentUser Employee employee
    ) {
        PRCommandService.request(prId, employee);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "생산요청 담당자 배정",
            description = "특정 생산요청에 담당자를 배정합니다."
    )
    @PutMapping("/{prId}/manager")
    public ResponseEntity<Void> assignManager(
            @PathVariable int prId,
            @RequestBody PRManagerAssignRequestDTO dto
    ) {
        PRCommandService.assignManager(prId, dto.getManagerId());
        return ResponseEntity.ok().build();
    }

}
