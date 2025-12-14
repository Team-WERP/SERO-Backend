package com.werp.sero.client.service;

import com.werp.sero.client.dto.ClientInfoResponseDTO;
import com.werp.sero.client.entity.Client;
import com.werp.sero.client.exception.ClientNotFoundException;
import com.werp.sero.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientInfoResponseDTO getClientInfo(int clientId) {
        // 1. 고객사 조회
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);

        // 2. DTO 변환 및 반환
        return ClientInfoResponseDTO.from(client);
    }
}
