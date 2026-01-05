package com.werp.sero.code.command.application.service;

import com.werp.sero.code.command.application.dto.CommonCodeCreateRequestDTO;
import com.werp.sero.code.command.application.dto.CommonCodeResponseDTO;
import com.werp.sero.code.command.application.dto.CommonCodeUpdateRequestDTO;
import com.werp.sero.code.command.domain.aggregate.CommonCode;
import com.werp.sero.code.command.domain.repository.CommonCodeRepository;
import com.werp.sero.code.command.domain.repository.CommonCodeTypeRepository;
import com.werp.sero.code.exception.CommonCodeAlreadyExistsException;
import com.werp.sero.code.exception.CommonCodeNotFoundException;
import com.werp.sero.code.exception.CommonCodeTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommonCodeCommandServiceImpl implements CommonCodeCommandService {

    private final CommonCodeRepository commonCodeRepository;
    private final CommonCodeTypeRepository commonCodeTypeRepository;

    @Override
    @Transactional
    public CommonCodeResponseDTO createCode(CommonCodeCreateRequestDTO request) {
        if (!commonCodeTypeRepository.existsByCode(request.getTypeCode())) {
            throw new CommonCodeTypeNotFoundException();
        }

        if (commonCodeRepository.existsByCode(request.getCode())) {
            throw new CommonCodeAlreadyExistsException();
        }

        CommonCode commonCode = CommonCode.builder()
                .typeCode(request.getTypeCode())
                .code(request.getCode())
                .name(request.getName())
                .parentCode(request.getParentCode())
                .sortOrder(request.getSortOrder())
                .description(request.getDescription())
                .isUsed(request.getIsUsed() != null ? request.getIsUsed() : true)
                .build();

        CommonCode savedCode = commonCodeRepository.save(commonCode);

        return new CommonCodeResponseDTO(savedCode);
    }

    @Override
    @Transactional
    public CommonCodeResponseDTO updateCode(String code, CommonCodeUpdateRequestDTO request) {
        CommonCode commonCode = commonCodeRepository.findByCode(code)
                .orElseThrow(CommonCodeNotFoundException::new);

        commonCode.update(
                request.getName(),
                request.getParentCode(),
                request.getSortOrder(),
                request.getDescription(),
                request.getIsUsed()
        );

        return new CommonCodeResponseDTO(commonCode);
    }

    @Override
    @Transactional
    public void deleteCode(String code) {
        CommonCode commonCode = commonCodeRepository.findByCode(code)
                .orElseThrow(CommonCodeNotFoundException::new);

        commonCodeRepository.delete(commonCode);
    }
}
