package com.werp.sero.code.command.application.service;

import com.werp.sero.code.command.application.dto.CommonCodeCreateRequestDTO;
import com.werp.sero.code.command.application.dto.CommonCodeResponseDTO;
import com.werp.sero.code.command.application.dto.CommonCodeUpdateRequestDTO;

public interface CommonCodeCommandService {

    CommonCodeResponseDTO createCode(CommonCodeCreateRequestDTO request);

    CommonCodeResponseDTO updateCode(String code, CommonCodeUpdateRequestDTO request);

    void deleteCode(String code);
}
