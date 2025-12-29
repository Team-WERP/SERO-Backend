package com.werp.sero.production.query.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.query.dto.*;
import com.werp.sero.production.query.service.PRQueryService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "생산요청 - Query",
        description = "생산요청 조회 API"
)
@RestController
@RequestMapping("/production-requests")
@RequiredArgsConstructor
public class PRQueryController {

    private final PRQueryService prQueryService;

    @Operation(
            summary = "임시 저장된 생산요청 목록 조회",
            description = "현재 로그인한 사용자가 작성한 임시 저장(PR_TMP) 상태의 생산요청 목록을 조회합니다."
    )
    @GetMapping("/drafts")
    public ResponseEntity<List<PRDraftListResponseDTO>> getDrafts(
            @CurrentUser Employee employee,
            @RequestParam(required = false) Integer soId,
            @RequestParam(required = false) String soCode
    ) {
        List<PRDraftListResponseDTO> result =
                prQueryService.getDraftsByDrafter(employee.getId(), soId, soCode);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "임시 저장된 생산요청 상세 조회",
            description = "현재 로그인한 사용자가 작성한 임시 저장(PR_TMP) 상태의 생산요청 상세 정보를 조회합니다."
    )
    @GetMapping("/drafts/{prId}")
    public ResponseEntity<PRDraftDetailResponseDTO> getDraftDetail(
            @PathVariable int prId,
            @CurrentUser Employee employee
    ) {
        return ResponseEntity.ok(
                prQueryService.getDraftDetail(prId, employee.getId())
        );
    }

    @Operation(
            summary = "생산요청 목록 조회",
            description = "필터 조건(기간/담당자/상태/키워드)으로 생산요청 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<PRListResponseDTO>> getList(
            @Parameter(
                    description = "생산요청일 (YYYY-MM-DD)",
                    example = "2025-12-19"
            )
            @RequestParam(required = false)
            String requestedDate,

            @Parameter(
                    description = "생산마감일 (YYYY-MM-DD)",
                    example = "2026-01-08"
            )
            @RequestParam(required = false)
            String dueDate,

            @Parameter(
                    description = "담당자 ID",
                    example = "1"
            )
            @RequestParam(required = false)
            Integer managerId,

            @Parameter(
                    description = "생산요청 상태 코드",
                    example = "PR_RVW"
            )
            @RequestParam(required = false)
            String status,

            @Parameter(
                    description = "검색 키워드 (PR코드 / SO코드 / 품목명)",
                    example = "스티어링"
            )
            @RequestParam(required = false)
            String keyword
    ) {
        PRListSearchCondition condition = PRListSearchCondition.builder()
                .requestedDate(requestedDate)
                .dueDate(dueDate)
                .managerId(managerId)
                .status(status)
                .keyword(keyword)
                .build();

        return ResponseEntity.ok(prQueryService.getPRList(condition));
    }

    @Operation(
            summary = "생산요청 상세 조회",
            description = "임시저장을 제외한 생산요청 상세 정보를 조회합니다."
    )
    @GetMapping("/{prId}")
    public ResponseEntity<PRDetailResponseDTO> getDetail(
            @PathVariable int prId
    ) {
        return ResponseEntity.ok(prQueryService.getDetail(prId));
    }

    @Operation(
            summary = "생산계획 수립 대상 PR Item 목록 조회",
            description = """
                특정 생산요청(PR)에 대해 생산계획을 수립할 수 있는 PR Item 목록을 조회합니다.
                각 PR Item별로 요청 수량, 기계획 수량, 잔여 수량 정보를 포함하며, 생산계획 수립 전 단계에서 사용됩니다.
                """
    )
    @GetMapping("/{prId}/plan-items")
    public ResponseEntity<PRPlanItemListResponseDTO> getPlanItems(
            @PathVariable int prId
    ) {
        return ResponseEntity.ok(
                prQueryService.getPlanItems(prId)
        );
    }

    @Operation(
            summary = "주문 id를 통한 생산 요청 조회",
            description = """
               주문 상세조회에서 해당 주문에 해당하는 생산 요청 목록을 조회합니다.
               상태가 임시저장인 것과 확정된 생산요청 모두 조회할 수 있습니다.
                """
    )
    @GetMapping("/search/{orderId}")
    public ResponseEntity<List<PRListResponseDTO>> getListByOrderId(
            @PathVariable int orderId
    ) {
        return ResponseEntity.ok(
                prQueryService.getListByOrderId(orderId)
        );
    }

}
