package com.werp.sero.code.command.application.service;

import com.werp.sero.code.command.application.dto.CommonCodeTypeCreateRequestDTO;
import com.werp.sero.code.command.application.dto.CommonCodeTypeResponseDTO;
import com.werp.sero.code.command.application.dto.CommonCodeTypeUpdateRequestDTO;
import com.werp.sero.code.command.domain.aggregate.CommonCodeType;
import com.werp.sero.code.command.domain.repository.CommonCodeRepository;
import com.werp.sero.code.command.domain.repository.CommonCodeTypeRepository;
import com.werp.sero.code.exception.CommonCodeTypeAlreadyExistsException;
import com.werp.sero.code.exception.CommonCodeTypeHasCodesException;
import com.werp.sero.code.exception.CommonCodeTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommonCodeTypeCommandServiceImpl implements CommonCodeTypeCommandService {

    private final CommonCodeTypeRepository commonCodeTypeRepository;
    private final CommonCodeRepository commonCodeRepository;

    @Override
    @Transactional
    public CommonCodeTypeResponseDTO createCodeType(CommonCodeTypeCreateRequestDTO request) {
        if (commonCodeTypeRepository.existsByCode(request.getCode())) {
            throw new CommonCodeTypeAlreadyExistsException();
        }

        CommonCodeType commonCodeType = CommonCodeType.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();

        CommonCodeType savedCodeType = commonCodeTypeRepository.save(commonCodeType);

        return new CommonCodeTypeResponseDTO(savedCodeType);
    }

    @Override
    @Transactional
    public CommonCodeTypeResponseDTO updateCodeType(String code, CommonCodeTypeUpdateRequestDTO request) {
        CommonCodeType commonCodeType = commonCodeTypeRepository.findByCode(code)
                .orElseThrow(CommonCodeTypeNotFoundException::new);

        commonCodeType.update(request.getName(), request.getDescription());

        return new CommonCodeTypeResponseDTO(commonCodeType);
    }

    @Override
    @Transactional
    public void deleteCodeType(String code) {
        CommonCodeType commonCodeType = commonCodeTypeRepository.findByCode(code)
                .orElseThrow(CommonCodeTypeNotFoundException::new);

        // 하위 공통코드가 존재하는지 확인
        if (commonCodeRepository.existsByTypeCode(code)) {
            throw new CommonCodeTypeHasCodesException();
        }

        commonCodeTypeRepository.delete(commonCodeType);
    }
}
