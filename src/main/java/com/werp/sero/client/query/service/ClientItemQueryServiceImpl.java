package com.werp.sero.client.query.service;

import com.werp.sero.client.exception.ClientItemNotFoundException;
import com.werp.sero.client.query.dao.ClientItemMapper;
import com.werp.sero.client.query.dao.ClientItemPriceHistoryMapper;
import com.werp.sero.client.query.dto.ClientItemPriceHistoryResponseDTO;
import com.werp.sero.client.query.dto.ClientItemResponseDTO;
import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.client.exception.ClientItemNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientItemQueryServiceImpl implements ClientItemQueryService {

    private final ClientItemMapper clientItemMapper;
    private final ClientItemPriceHistoryMapper clientItemPriceHistoryMapper;

    @Override
    public List<ClientItemResponseDTO> getClientItems(int clientId, String status, String keyword) {

            return clientItemMapper.findByClientId(clientId,keyword,status);
    }
    

    @Override
    public List<ClientItemPriceHistoryResponseDTO> getPriceHistory(int clientId, int itemId) {

        // 검증: 해당 품목이 해당 고객사의 거래 품목인지 확인
            if(!clientItemMapper.existsByIdAndClientId(clientId, itemId)) {

                throw new ClientItemNotFoundException(); 
            }

        return clientItemPriceHistoryMapper.findByClientIdAndClientItemIdOrderByCreatedAtDesc(clientId, itemId);
    }
}
