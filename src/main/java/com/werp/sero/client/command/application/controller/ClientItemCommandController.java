package com.werp.sero.client.command.application.controller;

import com.werp.sero.client.command.application.dto.ClientItemCreateRequest;
import com.werp.sero.client.command.application.dto.ClientItemCreateResponse;
import com.werp.sero.client.command.application.service.ClientItemCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객사 거래 품목 관리 API", description = "고객사 거래 품목 등록 API")
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
}
