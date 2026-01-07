package com.werp.sero.client.command.application.controller;

import com.werp.sero.client.command.application.dto.ClientItemCreateRequest;
import com.werp.sero.client.command.application.dto.ClientItemCreateResponse;
import com.werp.sero.client.command.application.dto.ClientItemUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientItemUpdateResponse;
import com.werp.sero.client.command.application.service.ClientItemCommandService;
import com.werp.sero.security.principal.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객사 거래 품목 관리 API", description = "고객사와 거래 중인 품목 및 계약 단가 관리 API")
@RestController
@RequestMapping("/clients-manage/{clientId}/items")
@RequiredArgsConstructor
public class ClientItemCommandController {

    private final ClientItemCommandService clientItemCommandService;

    @Operation(
            summary = "거래 품목 등록",
            description = "고객사와 거래할 품목을 등록하고 계약 단가를 설정합니다. " +
                    "영업팀이 고객사와 협의한 품목과 단가 정보를 기록하여 향후 주문 시 참고할 수 있습니다."
    )
    @PostMapping
    public ResponseEntity<ClientItemCreateResponse> createClientItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer clientId,
            @Valid @RequestBody ClientItemCreateRequest request
    ) {
        ClientItemCreateResponse response = clientItemCommandService.createClientItem(clientId, request, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "거래 품목 단가 수정",
            description = "고객사와 거래 중인 품목의 계약 단가를 수정합니다. " +
                    "가격 재협상이나 시장 상황 변화에 따라 기존 거래 품목의 단가를 조정할 수 있습니다."
    )
    @PutMapping("/{itemId}")
    public ResponseEntity<ClientItemUpdateResponse> updateClientItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer clientId,
            @PathVariable Integer itemId,
            @Valid @RequestBody ClientItemUpdateRequest request
    ) {
        ClientItemUpdateResponse response = clientItemCommandService.updateClientItem(clientId, itemId, request, userDetails.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "거래 품목 삭제",
            description = "고객사와의 거래 품목을 삭제합니다. " +
                    "더 이상 해당 고객사에게 공급하지 않는 품목이나 거래가 종료된 품목을 제거할 수 있습니다."
    )
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteClientItem(
            @PathVariable Integer clientId,
            @PathVariable Integer itemId
    ) {
        clientItemCommandService.deleteClientItem(clientId, itemId);
        return ResponseEntity.noContent().build();
    }
}
