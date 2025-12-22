package com.werp.sero.client.query.controller;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.werp.sero.client.exception.ClientAccessDeniedException;
import com.werp.sero.client.query.dto.ClientItemPriceHistoryResponseDTO;
import com.werp.sero.client.query.dto.ClientItemResponseDTO;
import com.werp.sero.client.query.service.ClientItemQueryService;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.security.annotation.CurrentUser;

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
        @RequestParam(required = false) String status,
        @CurrentUser ClientEmployee clientEmployee
    ) {
        // clientId 검증: 로그인한 ClientEmployee의 client_id와 요청한 clientId가 일치하는지 확인
        if (clientEmployee.getClient().getId() != clientId) {
            throw new ClientAccessDeniedException();
        }

        List<ClientItemResponseDTO> items = clientItemQueryService.getClientItems(clientId, keyword, status);
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "고객사 가격 변동 이력 조회")
    @GetMapping("/items/{itemId}/price-history")
    public ResponseEntity<List<ClientItemPriceHistoryResponseDTO>> getPriceHistory(
        @PathVariable int clientId,
        @PathVariable int itemId,
        @CurrentUser ClientEmployee clientEmployee
    ) {
        // clientId 검증: 로그인한 ClientEmployee의 client_id와 요청한 clientId가 일치하는지 확인
        if (clientEmployee.getClient().getId() != clientId) {
            throw new ClientAccessDeniedException();
        }

        List<ClientItemPriceHistoryResponseDTO> history =
            clientItemQueryService.getPriceHistory(clientId, itemId);
        return ResponseEntity.ok(history);
    }
}
