package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.WorkOrderResponseDTO;
import com.werp.sero.production.query.service.WOQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
@Tag(name = "작업지시 - Query")
public class WOQueryController {
    private final WOQueryService woQueryService;

    @Operation(summary = "생산계획 기준 작업지시 목록 조회")
    @GetMapping
    public ResponseEntity<List<WorkOrderResponseDTO>> getByProductionPlan(
            @RequestParam int ppId
    ) {
        return ResponseEntity.ok(
                woQueryService.getByProductionPlan(ppId)
        );
    }
}
