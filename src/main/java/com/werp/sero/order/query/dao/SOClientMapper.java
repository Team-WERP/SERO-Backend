package com.werp.sero.order.query.dao;

import com.werp.sero.order.command.application.dto.SOClientOrderDTO;
import com.werp.sero.order.query.dto.SOClientDetailResponseDTO;
import com.werp.sero.order.query.dto.SOClientListResponseDTO;
import com.werp.sero.order.query.dto.SOClientResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SOClientMapper {
    List<SOClientResponseDTO> selectOrderHistory(final int clientId);

    SOClientResponseDTO selectOrderForReorder(final int clientId, final int orderId);

    SOClientDetailResponseDTO selectOrderDetailForClient(final int orderId);

    List<SOClientListResponseDTO> selectAllClientSalesOrders(final Map<String, Object> params);
}
