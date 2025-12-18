package com.werp.sero.order.query.dao;

import com.werp.sero.order.query.dto.SOClientResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SOClientMapper {
    List<SOClientResponseDTO> selectOrderHistory(final int clientId);
}
