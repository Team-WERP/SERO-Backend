package com.werp.sero.order.query.service;

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
    public SOItemsHistoryResponseDTO findLatestOrderItemhistoryById(final int orderId) {
        SOItemsHistoryResponseDTO orderItemHistory = soMapper.selectLatestOrderItemHistory(orderId);

        if (orderItemHistory == null) {
            throw new SalesOrderNotFoundException();
        }
        return orderItemHistory;
    }

    @Override
    public List<SOItemsHistoryResponseDTO> findOrderItemhistory(final int orderId, final int itemId) {
        List<SOItemsHistoryResponseDTO> orderItemHistory = soMapper.selectOrderItemHistory(orderId, itemId);

        if (orderItemHistory == null) {
            throw new SalesOrderNotFoundException();
        }
        return orderItemHistory;
    }
}
