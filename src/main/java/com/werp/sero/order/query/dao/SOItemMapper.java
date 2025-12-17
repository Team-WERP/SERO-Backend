package com.werp.sero.order.query.dao;

import com.werp.sero.order.query.dto.SOItemResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SOItemMapper {
    List<SOItemResponseDTO> selectItemsBySalesOrderId(final int orderId);
}
