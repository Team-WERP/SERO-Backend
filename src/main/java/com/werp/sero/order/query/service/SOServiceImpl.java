package com.werp.sero.order.query.service;

import com.werp.sero.order.command.domain.repository.SOItemRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderItemHistoryNotFoundException;
import com.werp.sero.order.exception.SalesOrderItemNotFoundException;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.order.query.dao.SOMapper;
import com.werp.sero.order.query.dto.SOFilterDTO;
import com.werp.sero.order.query.dto.SODetailsResponseDTO;
import com.werp.sero.order.query.dto.SOItemsHistoryResponseDTO;
import com.werp.sero.order.query.dto.SOResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SOServiceImpl implements SOQueryService {

    private final SOMapper soMapper;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final SORepository salesOrderRepository;
    private final SOItemRepository salesOrderItemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SOResponseDTO> findOrderList(
    SOFilterDTO filter,
    Integer page) {

        Integer limit = DEFAULT_PAGE_SIZE;
        Integer offset = null;

        if (page != null && page >= 1) {
            offset = (page - 1) * limit;
        }

        Map<String, Object> params = new HashMap<>();

        params.put("statusType", filter.getStatusType());
        params.put("dateField", filter.getDateField());
        params.put("startDate", filter.getStartDate());
        params.put("endDate", filter.getEndDate());
        params.put("managerName", filter.getManagerName());
        params.put("clientName", filter.getClientName());
        params.put("searchKeyword", filter.getSearchKeyword());
        // 페이지네이션
        params.put("limit", limit);
        params.put("offset", offset);

        return soMapper.selectAllSalesOrders(params);
    }

    @Override
    @Transactional(readOnly = true)
    public SODetailsResponseDTO findOrderDetailsById(int orderId) {
        SODetailsResponseDTO orderDetails = soMapper.selectOrderDetailWithItems(orderId);

        if (orderDetails == null) {
            throw new SalesOrderNotFoundException();
        }

        return orderDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public SOItemsHistoryResponseDTO findAllItemsLatestHistory(final int orderId) {
        validateOrderAndItem(orderId, null);
        SOItemsHistoryResponseDTO response =  soMapper.selectOrderItemHistory(orderId, null, true);

        if (response == null) {
            throw new SalesOrderItemHistoryNotFoundException();
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public SOItemsHistoryResponseDTO findItemFullHistory(final int orderId, final int itemId) {
        validateOrderAndItem(orderId, itemId);
        SOItemsHistoryResponseDTO response = soMapper.selectOrderItemHistory(orderId, itemId, false);

        if (response == null) {
            throw new SalesOrderItemHistoryNotFoundException();
        }

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public SOItemsHistoryResponseDTO findItemLatestHistory(final int orderId, final int itemId) {
        validateOrderAndItem(orderId, itemId);
        SOItemsHistoryResponseDTO response =  soMapper.selectOrderItemHistory(orderId, itemId, true);

        return response;
    }

    private void validateOrderAndItem(int orderId, Integer itemId) {
        if (!salesOrderRepository.existsById(orderId)) {
            throw new SalesOrderNotFoundException();
        }

        if (itemId != null && itemId > 0) {
            if (!salesOrderItemRepository.existsByIdAndSalesOrderId(itemId, orderId)) {
                throw new SalesOrderItemNotFoundException();
            }
        }
    }
}
