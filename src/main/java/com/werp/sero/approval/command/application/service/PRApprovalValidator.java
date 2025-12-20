package com.werp.sero.approval.command.application.service;

import com.werp.sero.common.error.exception.EntityNotFoundException;
import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import com.werp.sero.production.command.domain.repository.PRRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PRApprovalValidator implements ApprovalRefCodeValidator {
    private static final String TYPE = "PR";

    private final PRRepository prRepository;

    @Override
    public boolean supports(final String targetType) {
        return TYPE.equals(targetType);
    }

    @Override
    public ProductionRequest validate(final String refCode) {
        // TODO PR(생산요청) 찾을 수 없다는 에러로 교체
        return prRepository.findByPrCode(refCode)
                .orElseThrow(EntityNotFoundException::new);
    }
}