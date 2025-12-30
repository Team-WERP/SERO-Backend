package com.werp.sero.shipping.query.service;

import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.shipping.exception.DeliveryOrderNotFoundException;
import com.werp.sero.shipping.query.dao.DOMapper;
import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.dto.DOListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DODetailQueryServiceImpl implements DODetailQueryService {

    private final DOMapper doMapper;
    private final SORepository  soRepository;

    @Override
    public DODetailResponseDTO getDeliveryOrderDetail(String doCode) {
        DODetailResponseDTO result = doMapper.findByDoCode(doCode);

        if (result == null) {
            throw new DeliveryOrderNotFoundException();
        }

        return result;
    }

    @Override
    public List<DOListResponseDTO> getDeliveryOrdersByStatusAndManager(String status, int managerId) {
        return doMapper.findByStatusAndManager(status, managerId);
    }

    @Override
    public List<DOListResponseDTO> getDeliveryOrdersByManager(int managerId) {
        return doMapper.findByManager(managerId);
    }

    @Override
    public List<DOListResponseDTO> getDeliveryOrderListByOrderId(final int orderId) {
        soRepository.findById(orderId).orElseThrow(SalesOrderNotFoundException::new);

        List<DOListResponseDTO> list = doMapper.selectDOListByOrderId(orderId);

        return list;
    }

    @Override
    public List<DOListResponseDTO> getAllDeliveryOrders() {
        return doMapper.findAll();
    }
}
