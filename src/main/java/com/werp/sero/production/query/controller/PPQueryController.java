package com.werp.sero.production.query.controller;

import com.werp.sero.production.command.application.dto.PPMonthlyPlanResponseDTO;
import com.werp.sero.production.query.dto.PPDailyPreviewResponseDTO;
import com.werp.sero.production.query.dto.PPUnassignedResponseDTO;
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
@RequestMapping("/production-plans")
@RequiredArgsConstructor
public class PPQueryController {
    private final PPQueryService ppQueryService;

    @Operation(
            summary = "PR Item 기준 생산계획 수립용 상세 조회",
            description = "선택한 PR Item에 대해 생산계획 수립에 필요한 상세 정보를 조회한다."
    )
    @GetMapping("/production-request-items/{prItemId}")
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

    @Operation(
            summary = "미편성 생산요청 목록 조회",
            description = """
            생산계획 수립 대상(PIS_TARGET) 중 아직 확정된 생산계획(PP_CONFIRMED)이 없는 생산요청 품목 목록을 조회한다.
            """
    )
    @GetMapping("/unassigned")
    public ResponseEntity<List<PPUnassignedResponseDTO>> getUnassignedTargets(
            @RequestParam(required = false) String month
    ) {
        return ResponseEntity.ok(
                ppQueryService.getUnassignedTargets(month)
        );
    }

    @Operation(summary = "월별 생산계획 조회")
    @GetMapping
    public ResponseEntity<List<PPMonthlyPlanResponseDTO>> getMonthlyPlans(
            @RequestParam String month
    ) {
        return ResponseEntity.ok(
                ppQueryService.getMonthlyPlans(month)
        );
    }

    @Operation(summary = "일자 기준 생산계획 + 작업지시 + 실적 미리보기")
    @GetMapping("/daily-preview")
    public ResponseEntity<List<PPDailyPreviewResponseDTO>> getDailyPreview(
            @RequestParam String date   // yyyy-MM-dd
    ) {
        return ResponseEntity.ok(
                ppQueryService.getDailyPreview(date)
        );
    }
}
