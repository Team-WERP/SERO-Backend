package com.werp.sero.client.service;

import com.werp.sero.client.dto.ClientAddressResponseDTO;
import com.werp.sero.client.dto.ClientInfoResponseDTO;
import com.werp.sero.client.entity.Client;
import com.werp.sero.client.entity.ClientAddress;
import com.werp.sero.client.exception.ClientNotFoundException;
import com.werp.sero.client.repository.ClientAddressRepository;
import com.werp.sero.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientAddressRepository clientAddressRepository;

    @Override
    public ClientInfoResponseDTO getClientInfo(int clientId) {
        // 1. 고객사 조회
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);

        // 2. DTO 변환 및 반환
        return ClientInfoResponseDTO.from(client);
    }

    @Override
    public List<ClientAddressResponseDTO> getClientAddresses(int clientId) {
        // 1. 고객사 존재 확인
        if (!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException();
        }

        // 2. 배송지 목록 조회 (기본 배송지 우선 정렬)
        List<ClientAddress> addresses = clientAddressRepository.findByClientIdOrderByDefault(clientId);

        // 3. DTO 변환 및 반환
        return addresses.stream()
                .map(ClientAddressResponseDTO::from)
                .collect(Collectors.toList());
    }
}
