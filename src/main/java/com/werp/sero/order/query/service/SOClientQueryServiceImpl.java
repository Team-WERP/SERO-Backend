package com.werp.sero.order.query.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderDTO;
import com.werp.sero.order.exception.InvalidSalesOrderIdException;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.order.query.dao.SOClientMapper;
import com.werp.sero.order.query.dao.SOMapper;
import com.werp.sero.order.query.dto.*;
import com.werp.sero.shipping.query.dao.DOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SOClientQueryServiceImpl implements SOClientQueryService {

    private final SOClientMapper soClientMapper;
    private final DOMapper doMapper;

    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    @Transactional(readOnly = true)
    public List<SOClientResponseDTO> findOrderHistory(final ClientEmployee clientEmployee) {
        int clientId = clientEmployee.getId();

        return soClientMapper.selectOrderHistory(clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public SOClientResponseDTO getOrderForReorder(final int orderId, final ClientEmployee clientEmployee) {
        int clientId = clientEmployee.getId();
        SOClientResponseDTO order = soClientMapper.selectOrderForReorder(clientId, orderId);

        if(order == null){
            throw new SalesOrderNotFoundException();
        }

        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SOClientListResponseDTO> findClientOrderList(
            ClientEmployee clientEmployee,
            SOClientFilterDTO filter,
            Integer page) {

        Integer limit = DEFAULT_PAGE_SIZE;
        Integer offset = null;

        if (page != null && page >= 1) {
            offset = (page - 1) * limit;
        }

        Map<String, Object> params = new HashMap<>();

        params.put("clientId", clientEmployee.getClient().getId());
        params.put("dateField", filter.getDateField());
        params.put("startDate", filter.getStartDate());
        params.put("endDate", filter.getEndDate());
        params.put("status", filter.getStatus());
        params.put("searchKeyword", filter.getSearchKeyword());

        params.put("limit", limit);
        params.put("offset", offset);

        List<SOClientListResponseDTO> orders = soClientMapper.selectAllClientSalesOrders(params);

        return orders.stream()
                .map(order -> SOClientListResponseDTO.builder()
                        .orderId(order.getOrderId())
                        .soCode(order.getSoCode())
                        .poCode(order.getPoCode())
                        .mainItemName(order.getMainItemName())
                        .totalPrice(order.getTotalPrice())
                        .orderedAt(order.getOrderedAt())
                        .shippedAt(order.getShippedAt())
                        .status(SOClientListResponseDTO.convertStatus(order.getStatus()))
                        .soUrl(order.getSoUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SOClientDetailResponseDTO findClientOrderDetail(final int orderId, final ClientEmployee clientEmployee) {

        SOClientDetailResponseDTO order = soClientMapper.selectOrderDetailForClient(orderId);

        if (order == null) {
            throw new SalesOrderNotFoundException();
        }

        if( order.getClientId() !=  clientEmployee.getClient().getId()){
            throw new InvalidSalesOrderIdException();
        }

        List<String> deliveryUrls = doMapper.selectDeliveryUrlsBySoId(orderId);

        return SOClientDetailResponseDTO.builder()
                .orderId(order.getOrderId())
                .soCode(order.getSoCode())
                .poCode(order.getPoCode())
                .status(SOClientListResponseDTO.convertStatus(order.getStatus()))
                .orderedAt(order.getOrderedAt())
                .shippedAt(order.getShippedAt())
                .soUrl(order.getSoUrl())
                .doUrls(deliveryUrls)
                .items(order.getItems())
                .totalItemCount(order.getItems().size())
                .totalQuantity(order.getTotalQuantity())
                .totalPrice(order.getTotalPrice())
                .managerName(order.getManagerName())
                .managerContact(order.getManagerContact())
                .shippingName(order.getShippingName())
                .address(order.getAddress())
                .recipientName(order.getRecipientName())
                .recipientContact(order.getRecipientContact())
                .note(order.getNote())
                .build();
    }


}
