package com.werp.sero.shipping.query.service;

import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.dto.DOListResponseDTO;

import java.util.List;

public interface DODetailQueryService {

    DODetailResponseDTO getDeliveryOrderDetail(String doCode);

    List<DOListResponseDTO> getDeliveryOrdersByStatusAndManager(String status, int managerId);

    List<DOListResponseDTO> getDeliveryOrderListByOrderId(final int orderId);
}
