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

    @Operation(
            summary = "일자 기준 작업지시 조회",
            description = "선택한 날짜에 라인별로 생성된 작업지시 목록과 아이템을 조회한다."
    )
    @GetMapping("/daily")
    public ResponseEntity<List<WorkOrderDailyResponseDTO>> getDaily(
            @RequestParam String date
    ) {
        return ResponseEntity.ok(
                woQueryService.getDailyWorkOrders(date)
        );
    }

    @Operation(summary = "라인 기준 긴급 작업 가능 PR Item 조회")
    @GetMapping("/emergency-targets")
    public ResponseEntity<List<WOEmergencyPRItemResponseDTO>> getEmergencyTargets(
            @RequestParam int lineId
    ) {
        return ResponseEntity.ok(
                woQueryService.getEmergencyTargets(lineId)
        );
    }

    @Operation(
            summary = "작업지시 실적 목록 조회 (생산관리자)",
            description = "실적이 등록된 작업지시만 기간/라인/검색어 기준으로 조회한다."
    )
    @GetMapping("/results")
    public ResponseEntity<List<WorkOrderResultListResponseDTO>> getResultList(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer lineId,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(
                woQueryService.getWorkOrderResultList(
                        startDate,
                        endDate,
                        lineId,
                        keyword
                )
        );
    }

}
