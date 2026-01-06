package com.werp.sero.client.command.application.service;

import com.werp.sero.client.command.application.dto.ClientItemCreateRequest;
import com.werp.sero.client.command.application.dto.ClientItemCreateResponse;
import com.werp.sero.client.command.application.dto.ClientItemUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientItemUpdateResponse;
import com.werp.sero.client.command.domain.aggregate.Client;
import com.werp.sero.client.command.domain.aggregate.ClientItem;
import com.werp.sero.client.command.domain.repository.ClientItemRepository;
import com.werp.sero.client.command.domain.repository.ClientRepository;
import com.werp.sero.client.query.dao.ClientItemMapper;
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
    private final ClientItemMapper clientItemMapper;

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

    @Override
    @Transactional
    public ClientItemUpdateResponse updateClientItem(Integer clientId, Integer itemId, ClientItemUpdateRequest request) {
        // 거래 품목 존재 여부 및 고객사 소속 확인
        ClientItem clientItem = clientItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLIENT_ITEM_NOT_FOUND));

        // 해당 품목이 요청한 고객사에 속하는지 검증
        if (!clientItemMapper.existsByIdAndClientId(clientId, itemId)) {
            throw new BusinessException(ErrorCode.CLIENT_ITEM_NOT_FOUND);
        }

        // 단가 수정
        clientItem.updateContractPrice(request.getContractPrice());

        // 응답 DTO 생성
        return ClientItemUpdateResponse.builder()
                .id(clientItem.getId())
                .clientId(clientItem.getClient().getId())
                .itemId(clientItem.getMaterial().getId())
                .itemCode(clientItem.getMaterial().getMaterialCode())
                .itemName(clientItem.getMaterial().getName())
                .contractPrice(clientItem.getContractPrice())
                .status(clientItem.getStatus())
                .build();
    }

    @Override
    @Transactional
    public void deleteClientItem(Integer clientId, Integer itemId) {
        // 거래 품목 존재 여부 및 고객사 소속 확인
        ClientItem clientItem = clientItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLIENT_ITEM_NOT_FOUND));

        // 해당 품목이 요청한 고객사에 속하는지 검증
        if (!clientItemMapper.existsByIdAndClientId(clientId, itemId)) {
            throw new BusinessException(ErrorCode.CLIENT_ITEM_NOT_FOUND);
        }

        // 삭제
        clientItemRepository.delete(clientItem);
    }
}
