package com.werp.sero.shipping.query.dao;

import com.werp.sero.shipping.query.dto.GIListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GIListMapper {


    List<GIListResponseDTO> selectAllGoodsIssues(Map<String, Object> params);

    List<GIListResponseDTO> selectGIListByOrderId(final int orderId);
}
