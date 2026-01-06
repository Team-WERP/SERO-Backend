package com.werp.sero.client.query.controller;

import com.werp.sero.client.query.dto.ClientDetailResponseDTO;
import com.werp.sero.client.query.dto.ClientListResponseDTO;
import com.werp.sero.client.query.service.ClientQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "고객사 관리 API", description = "고객사 목록/상세 조회 API")
@RestController
@RequestMapping("/clients-manage")
@RequiredArgsConstructor
public class ClientQueryController {

    private final ClientQueryService clientQueryService;

    @Operation(summary = "고객사 목록 조회", description = "모든 고객사 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ClientListResponseDTO>> getClientList() {
        List<ClientListResponseDTO> clients = clientQueryService.getClientList();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "고객사 상세 조회", description = "고객사 상세 정보를 조회합니다. (기본정보, 담당자, 여신, 배송지, 거래품목)")
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDetailResponseDTO> getClientDetail(@PathVariable Integer clientId) {
        ClientDetailResponseDTO client = clientQueryService.getClientDetail(clientId);
        return ResponseEntity.ok(client);
    }
}
