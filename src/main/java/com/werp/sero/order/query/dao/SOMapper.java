package com.werp.sero.order.query.dao;

import com.werp.sero.order.query.dto.SODetailsResponseDTO;
import com.werp.sero.order.query.dto.SOItemsHistoryResponseDTO;
import com.werp.sero.order.query.dto.SOResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SOMapper {
    List<SOResponseDTO> selectAllSalesOrders(final Map<String, Object> params);

    SODetailsResponseDTO selectOrderDetailWithItems(final int orderId);

    SOItemsHistoryResponseDTO selectOrderItemHistory(final int orderId, final Integer itemId, final boolean isLatestOnly);
}
