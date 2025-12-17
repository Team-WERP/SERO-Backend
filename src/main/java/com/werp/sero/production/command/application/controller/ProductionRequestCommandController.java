package com.werp.sero.production.command.application.controller;

import com.werp.sero.production.command.application.dto.ProductionRequestDraftCreateRequestDTO;
import com.werp.sero.production.command.application.service.ProductionRequestCommandService;
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
public class ProductionRequestCommandController {
    private final ProductionRequestCommandService productionRequestCommandService;

    @Operation(
            summary = "생산요청 임시저장 생성",
            description = """
                    수주(SO)를 기준으로 생산요청을 임시저장(PR_TMP) 상태로 생성합니다.
                            
                    - 최초 생성 시 상태는 PR_TMP(임시저장)
                    - requested_at 은 NULL
                    - 품목 정보는 선택 사항
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "임시저장 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            )
    })
    @PostMapping("/draft")
    public ResponseEntity<Integer> createDraft(
            @RequestBody ProductionRequestDraftCreateRequestDTO dto
    ) {
        int prId = productionRequestCommandService.createDraft(dto);
        return ResponseEntity.ok(prId);
    }
}
