package com.werp.sero.production.query.controller;

import com.werp.sero.production.command.domain.repository.WorkOrderResultRepository;
import com.werp.sero.production.query.dto.*;
import com.werp.sero.production.query.service.WOQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
@Tag(name = "작업지시 - Query")
public class WOQueryController {
    private final WOQueryService woQueryService;
    private final WorkOrderResultRepository workOrderResultRepository;

    @Operation(summary = "작업일자 기준 작업지시 조회 (1일)")
    @GetMapping("/by-date")
    public ResponseEntity<List<WOByDateResponseDTO>> getByDate(
            @RequestParam String date   // yyyy-MM-dd
    ) {
        return ResponseEntity.ok(
                woQueryService.getByDate(date)
        );
    }

    @Operation(summary = "작업지시 히스토리 조회")
    @GetMapping("/{woId}/history")
    public ResponseEntity<List<WorkOrderHistoryResponse>> getHistory(
            @PathVariable int woId
    ) {
        return ResponseEntity.ok(woQueryService.getHistory(woId));
    }

    @Operation(summary = "작업지시 실적 조회")
    @GetMapping("/{woId}/result")
    public ResponseEntity<WorkOrderResultResponse> getResult(
            @PathVariable int woId
    ) {
        return ResponseEntity.ok(
                woQueryService.getResult(woId)
        );
    }
}
