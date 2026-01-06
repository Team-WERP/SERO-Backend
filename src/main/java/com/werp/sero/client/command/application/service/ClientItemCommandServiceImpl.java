package com.werp.sero.client.command.application.service;

import com.werp.sero.client.command.application.dto.ClientItemCreateRequest;
import com.werp.sero.client.command.application.dto.ClientItemCreateResponse;
import com.werp.sero.client.command.domain.aggregate.Client;
import com.werp.sero.client.command.domain.aggregate.ClientItem;
import com.werp.sero.client.command.domain.repository.ClientItemRepository;
import com.werp.sero.client.command.domain.repository.ClientRepository;
import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.command.domain.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientItemCommandServiceImpl implements ClientItemCommandService {

    private final ClientItemRepository clientItemRepository;
    private final ClientRepository clientRepository;
    private final MaterialRepository materialRepository;

    @Override
    @Transactional
    public ClientItemCreateResponse createClientItem(Integer clientId, ClientItemCreateRequest request) {
        // 고객사 존재 여부 확인
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLIENT_NOT_FOUND));

        // 품목 존재 여부 확인
        Material material = materialRepository.findById(request.getItemId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MATERIAL_NOT_FOUND));

        // 거래 품목 생성
        ClientItem clientItem = ClientItem.builder()
                .client(client)
                .material(material)
                .contractPrice(request.getContractPrice())
                .build();

        // 저장
        ClientItem savedItem = clientItemRepository.save(clientItem);

        // 응답 DTO 생성
        return ClientItemCreateResponse.builder()
                .id(savedItem.getId())
                .clientId(client.getId())
                .itemId(material.getId())
                .itemCode(material.getMaterialCode())
                .itemName(material.getName())
                .contractPrice(savedItem.getContractPrice())
                .status(savedItem.getStatus())
                .build();
    }
}
