package com.werp.sero.client.command.application.controller;

import com.werp.sero.client.command.application.dto.ClientItemCreateRequest;
import com.werp.sero.client.command.application.dto.ClientItemCreateResponse;
import com.werp.sero.client.command.application.dto.ClientItemUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientItemUpdateResponse;
import com.werp.sero.client.command.application.service.ClientItemCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객사 거래 품목 관리 API", description = "고객사 거래 품목 등록/수정/삭제 API")
@RestController
@RequestMapping("/clients-manage/{clientId}/items")
@RequiredArgsConstructor
public class ClientItemCommandController {

    private final ClientItemCommandService clientItemCommandService;

    @Operation(summary = "고객사 거래 품목 등록", description = "고객사와 거래할 품목을 등록합니다.")
    @PostMapping
    public ResponseEntity<ClientItemCreateResponse> createClientItem(
            @PathVariable Integer clientId,
            @Valid @RequestBody ClientItemCreateRequest request
    ) {
        ClientItemCreateResponse response = clientItemCommandService.createClientItem(clientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "고객사 거래 품목 단가 수정", description = "고객사 거래 품목의 계약 단가를 수정합니다.")
    @PutMapping("/{itemId}")
    public ResponseEntity<ClientItemUpdateResponse> updateClientItem(
            @PathVariable Integer clientId,
            @PathVariable Integer itemId,
            @Valid @RequestBody ClientItemUpdateRequest request
    ) {
        ClientItemUpdateResponse response = clientItemCommandService.updateClientItem(clientId, itemId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "고객사 거래 품목 삭제", description = "고객사 거래 품목을 삭제합니다.")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteClientItem(
            @PathVariable Integer clientId,
            @PathVariable Integer itemId
    ) {
        clientItemCommandService.deleteClientItem(clientId, itemId);
        return ResponseEntity.noContent().build();
    }
}
