package com.werp.sero.code.query.service;

import com.werp.sero.code.query.dto.CommonCodeDetailManageDTO;
import com.werp.sero.code.query.dto.CommonCodeTypeManageDTO;

import java.util.List;

public interface CommonCodeManageQueryService {

    List<CommonCodeTypeManageDTO> getAllCodeTypes();

    List<CommonCodeDetailManageDTO> getCodeDetailsByType(String typeCode);

}
