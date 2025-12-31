package com.werp.sero.shipping.query.controller;

import com.werp.sero.shipping.query.dto.GIDetailResponseDTO;
import com.werp.sero.shipping.query.dto.GIFilterDTO;
import com.werp.sero.shipping.query.dto.GIListResponseDTO;
import com.werp.sero.shipping.query.service.GIDetailQueryService;
import com.werp.sero.shipping.query.service.GIListQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "출고지시", description = "출고지시 관련 API (Query)")
@RequestMapping("/goods-issues")
@RequiredArgsConstructor
@RestController
public class GIQueryController {

    private final GIListQueryService giListQueryService;
    private final GIDetailQueryService giDetailQueryService;

    @Operation(summary = "출고지시 목록 조회", description = "필터링 및 페이징을 지원하는 출고지시 목록 조회")
    @GetMapping
    public ResponseEntity<List<GIListResponseDTO>> findGoodsIssueList(
            @RequestParam(required = false) Integer warehouseId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Integer drafterId,
            @Parameter(description = "페이지 번호 (1부터 시작, 미입력시 전체 조회)", example = "1")
            @RequestParam(value = "page", required = false) Integer page) {

        GIFilterDTO filter = GIFilterDTO.builder()
                .warehouseId(warehouseId)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .searchKeyword(searchKeyword)
                .drafterId(drafterId)
                .build();

        return ResponseEntity.ok(giListQueryService.findGoodsIssueList(filter, page));
    }

    @Operation(summary = "출고지시서 상세 조회", description = "출고지시 ID로 출고지시서 상세 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<GIDetailResponseDTO> getGoodsIssueDetail(
            @Parameter(description = "출고지시 ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(giDetailQueryService.getGoodsIssueDetail(id));
    }


    @Operation(summary = "주문별 출고지시 목록 조회", description = "주문 id에 대한 출고지시 목록 조회")
    @GetMapping("/search/{orderId}")
    public ResponseEntity<List<GIListResponseDTO>> findGIListByOrderId(
            @PathVariable("orderId") final int orderId) {

        return ResponseEntity.ok(giListQueryService.findGIListByOrderId(orderId));
    }
}
