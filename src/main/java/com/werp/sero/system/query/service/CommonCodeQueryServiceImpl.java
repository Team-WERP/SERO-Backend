package com.werp.sero.system.query.service;

import com.werp.sero.system.query.dao.CommonCodeMapper;
import com.werp.sero.system.query.dto.CommonCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeQueryServiceImpl implements CommonCodeQueryService{
    private final CommonCodeMapper commonCodeMapper;

    @Override
    public CommonCodeDTO getByCode(String typeCode, String code) {
        CommonCodeDTO dto =
                commonCodeMapper.findByCode(typeCode, code);
        if (dto == null) {
            throw new IllegalArgumentException("공통코드를 찾을 수 없습니다.");
        }
        return dto;
    }

    @Override
    public String getName(String typeCode, String code) {
        String name =
                commonCodeMapper.findName(typeCode, code);
        if(name == null) {
            throw new IllegalArgumentException("공통코드 name을 찾을 수 없습니다.");
        }
        return name;
    }

    @Override
    public List<CommonCodeDTO> getCodes(String typeCode) {
        return commonCodeMapper.findByType(typeCode);
    }
}
