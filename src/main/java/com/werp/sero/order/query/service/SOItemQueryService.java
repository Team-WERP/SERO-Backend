package com.werp.sero.order.query.service;

import com.werp.sero.order.query.dto.SOItemResponseDTO;

import java.util.List;

public interface SOItemQueryService {
    List<SOItemResponseDTO> findOrderItemsById(final int orderId);
}
