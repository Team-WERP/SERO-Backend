package com.werp.sero.shipping.query.service;

import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.shipping.exception.GoodsIssueNotFoundException;
import com.werp.sero.shipping.query.dao.GIListMapper;
import com.werp.sero.shipping.query.dto.GIFilterDTO;
import com.werp.sero.shipping.query.dto.GIListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 출고지시 조회 서비스 구현체
 */
@RequiredArgsConstructor
@Service
public class GIListQueryServiceImpl implements GIListQueryService {

    private final GIListMapper giListMapper;
    private final SORepository soRepository;
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    @Transactional(readOnly = true)
    public List<GIListResponseDTO> findGoodsIssueList(GIFilterDTO filter, Integer page) {
        Integer limit = DEFAULT_PAGE_SIZE;
        Integer offset = null;

        if (page != null && page >= 1) {
            offset = (page - 1) * limit;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("warehouseId", filter.getWarehouseId());
        params.put("status", filter.getStatus());
        params.put("startDate", filter.getStartDate());
        params.put("endDate", filter.getEndDate());
        params.put("searchKeyword", filter.getSearchKeyword());
        params.put("limit", limit);
        params.put("offset", offset);

        return giListMapper.selectAllGoodsIssues(params);
    }

    @Override
    public List<GIListResponseDTO> findGIListByOrderId(final int orderId) {
        soRepository.findById(orderId).orElseThrow(SalesOrderNotFoundException::new);

        List<GIListResponseDTO> list = giListMapper.selectGIListByOrderId(orderId);
        return list;
    }

}
