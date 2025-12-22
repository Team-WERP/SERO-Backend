package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.WOByDateResponseDTO;
import com.werp.sero.production.query.dto.WOByPRResponseDTO;
import com.werp.sero.production.query.dto.WOByPPResponseDTO;
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

    @Operation(summary = "생산계획(PP) 기준 작업지시 목록 조회")
    @GetMapping("/by-pp")
    public ResponseEntity<List<WOByPPResponseDTO>> getByProductionPlan(
            @RequestParam int ppId
    ) {
        return ResponseEntity.ok(
                woQueryService.getByProductionPlan(ppId)
        );
    }

    @Operation(summary = "생산요청(PR) 기준 작업지시 목록 조회")
    @GetMapping("/by-pr")
    public ResponseEntity<List<WOByPRResponseDTO>> getByProductionRequest(
            @RequestParam int prId
    ) {
        return ResponseEntity.ok(
                woQueryService.getByProductionRequest(prId)
        );
    }

    @Operation(summary = "작업일자 기준 작업지시 조회 (1일)")
    @GetMapping("/by-date")
    public ResponseEntity<List<WOByDateResponseDTO>> getByDate(
            @RequestParam String date   // yyyy-MM-dd
    ) {
        return ResponseEntity.ok(
                woQueryService.getByDate(date)
        );
    }
}
