package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.PRItemPlanningResponseDTO;
import com.werp.sero.production.query.service.PPQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
