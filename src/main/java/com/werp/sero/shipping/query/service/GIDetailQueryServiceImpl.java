package com.werp.sero.shipping.query.service;

import com.werp.sero.shipping.exception.GoodsIssueNotFoundException;
import com.werp.sero.shipping.query.dao.GIMapper;
import com.werp.sero.shipping.query.dto.GIDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GIDetailQueryServiceImpl implements GIDetailQueryService {

    private final GIMapper giMapper;

    @Override
    @Transactional(readOnly = true)
    public GIDetailResponseDTO getGoodsIssueDetail(String giCode) {
        GIDetailResponseDTO result = giMapper.findByGiCode(giCode);

        if (result == null) {
            throw new GoodsIssueNotFoundException();
        }

        return result;
    }
}
