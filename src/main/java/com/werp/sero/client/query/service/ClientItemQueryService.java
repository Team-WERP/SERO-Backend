package com.werp.sero.client.query.service;

import com.werp.sero.client.query.dto.ClientItemPriceHistoryResponseDTO;
import com.werp.sero.client.query.dto.ClientItemResponseDTO;

import java.util.List;

public interface ClientItemQueryService {
    List<ClientItemResponseDTO> getClientItems(int clientId, String keyword, String status);
    List<ClientItemPriceHistoryResponseDTO> getPriceHistory(int clientId, int itemId);
}
