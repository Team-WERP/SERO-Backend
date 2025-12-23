package com.werp.sero.approval.command.application.service;

import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SOApprovalValidator implements ApprovalRefCodeValidator {
    private static final String TYPE = "SO";

    private final SORepository soRepository;

    @Override
    public boolean supports(final String targetType) {
        return TYPE.equals(targetType);
    }

    @Override
    public SalesOrder validate(final String refCode) {
        return soRepository.findBySoCode(refCode)
                .orElseThrow(SalesOrderNotFoundException::new);
    }
}