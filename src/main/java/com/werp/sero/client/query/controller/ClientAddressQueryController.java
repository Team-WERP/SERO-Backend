package com.werp.sero.client.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.werp.sero.client.command.application.service.ClientAddressCommandService;
import com.werp.sero.client.query.dto.ClientAddressResponseDTO;
import com.werp.sero.client.query.service.ClientAddressQueryService;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.security.annotation.CurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "고객사 배송지 관련 API", description = "고객사 배송지 관련 API")
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor
@RestController
public class ClientAddressQueryController {

    private final ClientAddressQueryService clientAddressQueryService;
    private final ClientAddressCommandService clientAddressCommandService;

    @Operation(summary = "고객사 배송지 조회")
    @GetMapping("/addresses")
    public ResponseEntity<List<ClientAddressResponseDTO>> getClientAddresses(
        @PathVariable int clientId,
        @CurrentUser ClientEmployee clientEmployee
    ) {
        clientAddressCommandService.validateClientAccess(clientEmployee, clientId);

        List<ClientAddressResponseDTO> addresses = clientAddressQueryService.getClientAddresses(clientId);
        return ResponseEntity.ok(addresses);
    }
}
