package com.werp.sero.shipping.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.shipping.command.application.dto.GIAssignManagerResponseDTO;
import com.werp.sero.shipping.command.application.dto.GICompleteResponseDTO;
import com.werp.sero.shipping.command.application.dto.GICreateRequestDTO;
import com.werp.sero.shipping.command.application.dto.GICreateResponseDTO;

public interface GoodsIssueCommandService {
    GICreateResponseDTO createGoodsIssue(GICreateRequestDTO requestDTO, Employee drafter);
    GICompleteResponseDTO completeGoodsIssue(String giCode);
    GIAssignManagerResponseDTO assignManager(String giCode, int empId);
}
