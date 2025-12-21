package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.PRItemPlanningResponseDTO;
import com.werp.sero.production.query.dto.ProductionLineResponseDTO;
import com.werp.sero.production.query.service.PPQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "생산계획 - Query",
        description = "생산계획 관련 조회 API"
)
@RestController
@RequiredArgsConstructor
public class PPQueryController {
    private final PPQueryService ppQueryService;

    @Operation(
            summary = "PR Item 기준 생산계획 수립용 상세 조회",
            description = "선택한 PR Item에 대해 생산계획 수립에 필요한 상세 정보를 조회한다."
    )
    @GetMapping("/production-request-items/{prItemId}/planning")
    public ResponseEntity<PRItemPlanningResponseDTO> getPlanning(
            @PathVariable int prItemId
    ) {
        return ResponseEntity.ok(
                ppQueryService.getPlanning(prItemId)
        );
    }

    @Operation(
            summary = "생산라인 목록 조회",
            description = """
                생산계획 수립 시 선택 가능한 생산라인 목록을 조회한다.
                활성 상태(PL_ACTIVE)인 라인만 반환한다.
                """
    )
    @GetMapping("/production-lines")
    public ResponseEntity<List<ProductionLineResponseDTO>> getProductionLines(
            @Parameter(
                    description = "공장 ID (선택)",
                    example = "2"
            )
            @RequestParam(required = false) Integer factoryId
    ) {
        return ResponseEntity.ok(
                ppQueryService.getProductionLines(factoryId)
        );
    }
}
