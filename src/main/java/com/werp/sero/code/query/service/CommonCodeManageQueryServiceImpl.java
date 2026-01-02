package com.werp.sero.code.query.service;

import com.werp.sero.code.query.dao.CommonCodeManageMapper;
import com.werp.sero.code.query.dto.CommonCodeDetailManageDTO;
import com.werp.sero.code.query.dto.CommonCodeTypeManageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeManageQueryServiceImpl implements CommonCodeManageQueryService{
    private final CommonCodeManageMapper commonCodeManageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommonCodeTypeManageDTO> getAllCodeTypes() {
        return commonCodeManageMapper.findAllCodeTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommonCodeDetailManageDTO> getCodeDetailsByType(String typeCode) {
        return commonCodeManageMapper.findDetailsByType(typeCode);
    }
}
