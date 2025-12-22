package com.werp.sero.approval.command.application.service;

import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import com.werp.sero.shipping.command.domain.repository.GIRepository;
import com.werp.sero.shipping.exception.GoodsIssueNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GIApprovalValidator implements ApprovalRefCodeValidator {
    private static final String TYPE = "GI";

    private final GIRepository GIRepository;

    @Override
    public boolean supports(final String targetType) {
        return TYPE.equals(targetType);
    }

    @Override
    public GoodsIssue validate(final String refCode) {
        return GIRepository.findByGiCode(refCode)
                .orElseThrow(GoodsIssueNotFoundException::new);
    }
}