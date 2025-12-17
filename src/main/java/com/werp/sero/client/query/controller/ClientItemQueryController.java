package com.werp.sero.client.query.controller;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.werp.sero.client.query.dto.ClientItemPriceHistoryResponseDTO;
import com.werp.sero.client.query.dto.ClientItemResponseDTO;
import com.werp.sero.client.query.service.ClientItemQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "고객사 거래 품목 관련 API", description = "고객사 거래 품목 관련 API")
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor
@RestController
public class ClientItemQueryController {

    private final ClientItemQueryService clientItemQueryService;

    @Operation(summary = "고객사 거래 가능 품목 조회")
    @GetMapping("/items")
    public ResponseEntity<List<ClientItemResponseDTO>> getClientItems(

        @PathVariable int clientId,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String status
    ) {
        // service 호출
        List<ClientItemResponseDTO> items = clientItemQueryService.getClientItems(clientId, keyword, status);
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "고객사 가격 변동 이력 조회")
    @GetMapping("/items/{itemId}/price-history")
    public ResponseEntity<List<ClientItemPriceHistoryResponseDTO>> getPriceHistory(

        @PathVariable int clientId,
        @PathVariable int itemId
    ) {

        List<ClientItemPriceHistoryResponseDTO> history =
            clientItemQueryService.getPriceHistory(clientId, itemId);
        return ResponseEntity.ok(history);
    }
}
