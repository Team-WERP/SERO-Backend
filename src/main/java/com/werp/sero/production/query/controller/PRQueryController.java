package com.werp.sero.production.query.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.query.dto.*;
import com.werp.sero.production.query.service.PRQueryService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
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
            @RequestParam(required = false) String requestedFrom,
            @RequestParam(required = false) String requestedTo,
            @RequestParam(required = false) String dueFrom,
            @RequestParam(required = false) String dueTo,
            @RequestParam(required = false) Integer managerId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword
    ) {
        PRListSearchCondition condition = PRListSearchCondition.builder()
                .requestedFrom(requestedFrom)
                .requestedTo(requestedTo)
                .dueFrom(dueFrom)
                .dueTo(dueTo)
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

}
