package com.werp.sero.client.command.application.service;


import com.werp.sero.client.command.application.dto.ClientAddressCreateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressCreateResponse;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateResponse;
import com.werp.sero.client.command.domain.aggregate.Client;
import com.werp.sero.client.command.domain.aggregate.ClientAddress;
import com.werp.sero.client.command.domain.repository.ClientAddressCommandRepository;
import com.werp.sero.client.command.domain.repository.ClientRepository;
import com.werp.sero.client.exception.ClientAddressNotFoundException;
import com.werp.sero.client.exception.ClientNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientAddressCommandServiceImpl implements ClientAddressCommandService {


    private final ClientAddressCommandRepository  clientAddressCommandRepository;
    private final ClientRepository clientRepository;


    @Override
    @Transactional
    public ClientAddressCreateResponse createAddress(int clientId, ClientAddressCreateRequest request) {

        // 1. Client 조회 (임시)
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);

        // 2. 현재 시간
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 3. ClientAddress 생성
        ClientAddress clientAddress = ClientAddress.builder()
                .name(request.getName())
                .address(request.getAddress())
                .latitude(null)  // 프론트에서 나중에 보낼 예정
                .longitude(null) // 프론트에서 나중에 보낼 예정
                .recipientName(request.getRecipientName())
                .recipientContact(request.getRecipientContact())
                .isDefault(request.isDefault())
                .createdAt(now)
                .client(client)
                .build();


        // 4. 저장
        ClientAddress savedAddress = clientAddressCommandRepository.save(clientAddress);


        // 5. Response 반환
        return ClientAddressCreateResponse.builder()
                .id(savedAddress.getId())
                .name(savedAddress.getName())
                .address(savedAddress.getAddress())
                .recipientName(savedAddress.getRecipientName())
                .recipientContact(savedAddress.getRecipientContact())
                .isDefault(savedAddress.isDefault())
                .build();
    }

    @Override
    @Transactional
    public ClientAddressUpdateResponse updateAddress(int clientId, int addressId ,ClientAddressUpdateRequest request) {
        // 1. Client 조회 (임시)
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);

        // 2. 기존 ClientAddress 조회
        ClientAddress clientAddress = clientAddressCommandRepository.findById(addressId)
                .orElseThrow(ClientAddressNotFoundException::new);

        // 2. 현재 시간
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 3. ClientAddress 업데이트
        clientAddress.update(
                request.getName(),
                request.getAddress(),
                request.getRecipientName(),
                request.getRecipientContact(),
                request.isDefault(),
                now
        );


        // 4. 저장
        ClientAddress savedAddress = clientAddressCommandRepository.save(clientAddress);


        // 5. Response 반환
        return ClientAddressUpdateResponse.builder()
                .id(savedAddress.getId())
                .name(savedAddress.getName())
                .address(savedAddress.getAddress())
                .recipientName(savedAddress.getRecipientName())
                .recipientContact(savedAddress.getRecipientContact())
                .isDefault(savedAddress.isDefault())
                .build();
    }

    @Override
    @Transactional
    public void deleteAddress(int clientId, int addressId) {

        // 1. Client 조회 (임시)
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);

        // 2. 기존 ClientAddress 조회
        ClientAddress clientAddress = clientAddressCommandRepository.findById(addressId)
                .orElseThrow(ClientAddressNotFoundException::new);

        // 3. ClientAddress 삭제
        clientAddressCommandRepository.delete(clientAddress);
    }
}
