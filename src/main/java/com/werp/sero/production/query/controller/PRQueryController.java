package com.werp.sero.production.query.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;
import com.werp.sero.production.query.service.PRQueryService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @CurrentUser Employee employee
    ) {
        List<PRDraftListResponseDTO> result =
                prQueryService.getDraftsByDrafter(employee.getId());
        return ResponseEntity.ok(result);
    }
}
