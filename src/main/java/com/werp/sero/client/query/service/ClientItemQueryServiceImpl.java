package com.werp.sero.client.query.service;

import com.werp.sero.client.query.dao.ClientItemMapper;
import com.werp.sero.client.query.dao.ClientItemPriceHistoryMapper;
import com.werp.sero.client.query.dto.ClientItemPriceHistoryResponseDTO;
import com.werp.sero.client.query.dto.ClientItemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientItemQueryServiceImpl implements ClientItemQueryService {

    private final ClientItemMapper clientItemMapper;
    private final ClientItemPriceHistoryMapper clientItemPriceHistoryMapper;

    @Override
    public List<ClientItemResponseDTO> getClientItems(int clientId, String keyword, String status) {

        if (keyword != null && status != null) {
            return clientItemMapper.findByClientIdAndStatusAndKeyword(clientId, status, keyword);
        }
        else if (keyword != null) {
            return clientItemMapper.findByClientIdAndKeyword(clientId, keyword);
        }
        else if (status != null) {
            return clientItemMapper.findByClientIdAndStatus(clientId, status);
        }
        else {
            return clientItemMapper.findByClientId(clientId);
        }
    }

    @Override
    public List<ClientItemPriceHistoryResponseDTO> getPriceHistory(int clientId, int itemId) {
        return clientItemPriceHistoryMapper.findByClientIdAndClientItemIdOrderByCreatedAtDesc(clientId, itemId);
    }
}
