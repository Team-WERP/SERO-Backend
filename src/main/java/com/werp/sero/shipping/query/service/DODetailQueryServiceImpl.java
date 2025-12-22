package com.werp.sero.shipping.query.service;

import com.werp.sero.shipping.exception.DeliveryOrderNotFoundException;
import com.werp.sero.shipping.query.dao.DOMapper;
import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DODetailQueryServiceImpl implements DODetailQueryService {

    private final DOMapper doMapper;

    @Override
    public DODetailResponseDTO getDeliveryOrderDetail(String doCode) {
        DODetailResponseDTO result = doMapper.findByDoCode(doCode);

        if (result == null) {
            throw new DeliveryOrderNotFoundException();
        }

        return result;
    }
}
