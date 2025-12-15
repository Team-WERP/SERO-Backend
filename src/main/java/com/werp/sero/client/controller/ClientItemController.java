package com.werp.sero.client.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.werp.sero.client.dto.ClientItemPriceHistoryResponse;
import com.werp.sero.client.dto.ClientItemResponse;
import com.werp.sero.client.entity.ClientItemPriceHistory;
import com.werp.sero.client.service.ClientItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag (name = "고객사 거래 품목 관련 API", description = "고객사 거래 품목 관련 API")
@RequestMapping
@RequiredArgsConstructor
@RestController
public class ClientItemController {

    private final ClientItemService clientItemService;

    @Operation(summary = "고객사 거래 가능 품목 조회")
    @GetMapping ("clients/{clientId}/items")
    public ResponseEntity<List<ClientItemResponse>> getClientItems(

        @PathVariable int clientId,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String status
    ) {
        // service 호출
        List<ClientItemResponse> items = clientItemService.getClientItems(clientId,keyword,status);
        return ResponseEntity.ok(items);
    }


    @Operation(summary = "고객사 가격 변동 이력 조회")
    @GetMapping("clients/{clientId}/items/{itemId}/price-history")
    public ResponseEntity<List<ClientItemPriceHistoryResponse>> getPriceHistory(

        @PathVariable int clientId, 
        @PathVariable int itemId
    ) {
    
        List<ClientItemPriceHistoryResponse> history = 
            clientItemService.getPriceHistory(clientId, itemId);
        return ResponseEntity.ok(history);
    }

}