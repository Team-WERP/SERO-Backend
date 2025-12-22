package com.werp.sero.client.command.application.controller;

import com.werp.sero.client.command.application.dto.ClientAddressCreateResponse;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateResponse;
import com.werp.sero.client.command.application.service.ClientAddressCommandService;
import com.werp.sero.client.exception.ClientAccessDeniedException;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.security.annotation.CurrentUser;
import lombok.AccessLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.werp.sero.client.command.application.dto.ClientAddressCreateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "고객사 배송지 관련 API", description = "고객사 배송지 관련 API")
@RestController
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor
public class ClientAddressCommandController {

    private final ClientAddressCommandService clientAddressCommandService;

    @Operation(summary = "고객사 신규 배송지 등록" , description = "모달창을 통해 신규 배송지 등록")
    @PostMapping("/addresses")
    public ResponseEntity<ClientAddressCreateResponse> createNewAddress(
        @PathVariable int clientId,
        @RequestBody @Valid ClientAddressCreateRequest request,
        @CurrentUser ClientEmployee clientEmployee
    ) {
        // clientId 검증: 로그인한 ClientEmployee의 client_id와 요청한 clientId가 일치하는지 확인
        if (clientEmployee.getClient().getId() != clientId) {
            throw new ClientAccessDeniedException();
        }

        ClientAddressCreateResponse response =
            clientAddressCommandService.createAddress(clientId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "고객사 배송지 수정" , description = "모달창을 통해 배송지 정보 수정")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<ClientAddressUpdateResponse> updateAddress(
        @PathVariable int clientId,
        @PathVariable int addressId,
        @RequestBody ClientAddressUpdateRequest request,
        @CurrentUser ClientEmployee clientEmployee
    ) {
        // clientId 검증: 로그인한 ClientEmployee의 client_id와 요청한 clientId가 일치하는지 확인
        if (clientEmployee.getClient().getId() != clientId) {
            throw new ClientAccessDeniedException();
        }

        ClientAddressUpdateResponse response =
                clientAddressCommandService.updateAddress(clientId, addressId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "고객사 배송지 삭제", description = "배송지 정보 삭제")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(
        @PathVariable int clientId,
        @PathVariable int addressId,
        @CurrentUser ClientEmployee clientEmployee
    ) {
        // clientId 검증: 로그인한 ClientEmployee의 client_id와 요청한 clientId가 일치하는지 확인
        if (clientEmployee.getClient().getId() != clientId) {
            throw new ClientAccessDeniedException();
        }

        clientAddressCommandService.deleteAddress(clientId, addressId);
        return ResponseEntity.ok().build();
    }
}
