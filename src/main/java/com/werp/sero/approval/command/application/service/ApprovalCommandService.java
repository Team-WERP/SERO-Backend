package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.ApprovalCreateRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalResponseDTO;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApprovalCommandService {
    ApprovalResponseDTO submitForApproval(final Employee employee, final ApprovalCreateRequestDTO requestDTO,
                                          final List<MultipartFile> files);
}