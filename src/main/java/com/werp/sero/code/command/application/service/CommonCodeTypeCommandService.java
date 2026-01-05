package com.werp.sero.code.command.application.service;

import com.werp.sero.code.command.application.dto.CommonCodeTypeCreateRequestDTO;
import com.werp.sero.code.command.application.dto.CommonCodeTypeResponseDTO;
import com.werp.sero.code.command.application.dto.CommonCodeTypeUpdateRequestDTO;

public interface CommonCodeTypeCommandService {

    CommonCodeTypeResponseDTO createCodeType(CommonCodeTypeCreateRequestDTO request);

    CommonCodeTypeResponseDTO updateCodeType(String code, CommonCodeTypeUpdateRequestDTO request);

    void deleteCodeType(String code);
}
