package com.werp.sero.shipping.query.dao;

import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.dto.DOListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DOMapper {

    DODetailResponseDTO findByDoCode(@Param("doCode") String doCode);

    List<DOListResponseDTO> findByStatusAndManager(@Param("status") String status, @Param("managerId") int managerId);

    List<String> selectDeliveryUrlsBySoId(final int orderId);
}
