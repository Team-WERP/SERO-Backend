package com.werp.sero.shipping.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.shipping.command.application.dto.GICompleteResponseDTO;
import com.werp.sero.shipping.command.application.dto.GICreateRequestDTO;

public interface GoodsIssueCommandService {
    String createGoodsIssue(GICreateRequestDTO requestDTO, Employee drafter);
    GICompleteResponseDTO completeGoodsIssue(String giCode);
}
