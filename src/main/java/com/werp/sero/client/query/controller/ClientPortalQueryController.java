package com.werp.sero.client.query.controller;

import com.werp.sero.client.query.dto.ClientDetailResponseDTO;
import com.werp.sero.client.query.dto.ClientPortalDetailResponseDTO;
import com.werp.sero.client.query.service.ClientQueryService;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "고객포털 관련 API", description = "고객포털 관련 API")
@RequestMapping("/clients/{clientId}")
@RequiredArgsConstructor
@RestController
public class ClientPortalQueryController {
    private final ClientQueryService clientQueryService;

    @Operation(summary = "고객사 상세 조회", description = "고객사 자신의 상세 정보를 조회합니다. (기본정보, 담당자, 여신)")
    @GetMapping("/details")
    public ResponseEntity<ClientPortalDetailResponseDTO> getClientDetail(
            @PathVariable("clientId") int clientId
    ) {
        ClientPortalDetailResponseDTO client = clientQueryService.getClientDetailForClient(clientId);
        return ResponseEntity.ok(client);
    }
}
