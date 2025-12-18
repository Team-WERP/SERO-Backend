package com.werp.sero.order.query.dao;

import com.werp.sero.order.query.dto.SODetailsResponseDTO;
import com.werp.sero.order.query.dto.SOResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SOMapper {
    List<SOResponseDTO> selectAllSalesOrders(Map<String, Object> params);

    SODetailsResponseDTO selectOrderDetailById(final int orderId);
}
