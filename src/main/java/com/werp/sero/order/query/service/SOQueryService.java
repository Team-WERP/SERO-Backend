package com.werp.sero.order.query.service;

import com.werp.sero.order.query.dto.SOFilterDTO;
import com.werp.sero.order.query.dto.SODetailsResponseDTO;
import com.werp.sero.order.query.dto.SOItemsHistoryResponseDTO;
import com.werp.sero.order.query.dto.SOResponseDTO;

import java.util.List;

public interface SOQueryService {
    List<SOResponseDTO> findOrderList(final SOFilterDTO filter, final Integer page);

    SODetailsResponseDTO findOrderDetailsById(final int orderId);

    SOItemsHistoryResponseDTO findAllItemsLatestHistory(final int orderId);

    SOItemsHistoryResponseDTO findItemFullHistory(final int orderId, final int itemId);

    SOItemsHistoryResponseDTO findItemLatestHistory(final int orderId, final int itemId);
}
