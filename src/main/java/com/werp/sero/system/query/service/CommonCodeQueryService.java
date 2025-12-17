package com.werp.sero.system.query.service;

import com.werp.sero.system.query.dto.CommonCodeDTO;

import java.util.List;

public interface CommonCodeQueryService {
    CommonCodeDTO getByCode(String typeCode, String code);
    String getName(String typeCode, String code);
    List<CommonCodeDTO> getCodes(String typeCode);
}
