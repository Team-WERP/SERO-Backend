package com.werp.sero.client.query.service;

import com.werp.sero.client.query.dao.ClientAddressMapper;
import com.werp.sero.client.query.dao.ClientItemMapper;
import com.werp.sero.client.query.dao.ClientMapper;
import com.werp.sero.client.query.dto.ClientAddressResponseDTO;
import com.werp.sero.client.query.dto.ClientDetailResponseDTO;
import com.werp.sero.client.query.dto.ClientItemResponseDTO;
import com.werp.sero.client.query.dto.ClientListResponseDTO;
import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientQueryServiceImpl implements ClientQueryService {

    private final ClientMapper clientMapper;
    private final ClientAddressMapper clientAddressMapper;
    private final ClientItemMapper clientItemMapper;

    @Override
    public List<ClientListResponseDTO> getClientList() {
        return clientMapper.findAllClients();
    }

    @Override
    public ClientDetailResponseDTO getClientDetail(Integer clientId) {
        // 고객사 기본 정보 조회
        ClientDetailResponseDTO client = clientMapper.findClientById(clientId);

        if (client == null) {
            throw new BusinessException(ErrorCode.CLIENT_NOT_FOUND);
        }

        // 배송지 목록 조회
        List<ClientAddressResponseDTO> addresses = clientAddressMapper.findByClientId(clientId);

        // 거래 품목 목록 조회 (status: null = 전체, keyword: null = 전체)
        List<ClientItemResponseDTO> items = clientItemMapper.findByClientId(clientId, null, null);

        // DTO 조합 (Builder 패턴 사용)
        return ClientDetailResponseDTO.builder()
                .id(client.getId())
                .companyName(client.getCompanyName())
                .businessNumber(client.getBusinessNumber())
                .businessType(client.getBusinessType())
                .businessItem(client.getBusinessItem())
                .ceoName(client.getCeoName())
                .phoneNumber(client.getPhoneNumber())
                .managerName(client.getManagerName())
                .managerContact(client.getManagerContact())
                .managerEmail(client.getManagerEmail())
                .creditLimit(client.getCreditLimit())
                .receivables(client.getReceivables())
                .unpaidAmount(client.getUnpaidAmount())
                .addresses(addresses)
                .items(items)
                .build();
    }
}
