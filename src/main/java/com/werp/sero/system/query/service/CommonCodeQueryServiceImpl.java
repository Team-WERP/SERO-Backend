package com.werp.sero.system.query.service;

import com.werp.sero.system.exception.SystemCommonCodeNotFoundException;
import com.werp.sero.system.query.dao.CommonCodeMapper;
import com.werp.sero.system.query.dto.CommonCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonCodeQueryServiceImpl implements CommonCodeQueryService{
    private final CommonCodeMapper commonCodeMapper;

    @Override
    public CommonCodeDTO getByCode(String typeCode, String code) {
        return Optional.ofNullable(
                commonCodeMapper.findByCode(typeCode, code)
        ).orElseThrow(SystemCommonCodeNotFoundException::new);
    }

    @Override
    public String getName(String typeCode, String code) {
        return Optional.ofNullable(
                commonCodeMapper.findName(typeCode, code)
        ).orElseThrow(SystemCommonCodeNotFoundException::new);
    }

    @Override
    public List<CommonCodeDTO> getCodes(String typeCode) {
        return commonCodeMapper.findByType(typeCode);
    }
}
