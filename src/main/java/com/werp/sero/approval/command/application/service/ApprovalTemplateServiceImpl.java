package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.*;
import com.werp.sero.approval.command.domain.aggregate.ApprovalTemplate;
import com.werp.sero.approval.command.domain.aggregate.ApprovalTemplateLine;
import com.werp.sero.approval.command.domain.repository.ApprovalTemplateLineRepository;
import com.werp.sero.approval.command.domain.repository.ApprovalTemplateRepository;
import com.werp.sero.approval.exception.ApprovalLineSequenceDuplicatedException;
import com.werp.sero.approval.exception.ApprovalLineSequenceNotAllowedException;
import com.werp.sero.approval.exception.ApprovalLineSequenceRequiredException;
import com.werp.sero.approval.exception.ApprovalTemplateNameDuplicatedException;
import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApprovalTemplateServiceImpl implements ApprovalTemplateService {
    private static final String APPROVAL_TYPE_APPROVAL = "AT_APPR";
    private static final String APPROVAL_TYPE_REVIEWER = "AT_RVW";
    private static final String APPROVAL_TYPE_REFERENCE = "AT_REF";
    private static final String APPROVAL_TYPE_RECIPIENT = "AT_RCPT";

    private final EmployeeRepository employeeRepository;
    private final ApprovalTemplateRepository approvalTemplateRepository;
    private final ApprovalTemplateLineRepository approvalTemplateLineRepository;

    @Transactional
    @Override
    public ApprovalTemplateResponseDTO registerApprovalTemplate(final Employee employee, final ApprovalTemplateCreateRequestDTO requestDTO) {
        validateApprovalTemplateName(employee, requestDTO.getName());

        ApprovalTemplate approvalTemplate = new ApprovalTemplate(requestDTO.getName(), requestDTO.getDescription(),
                employee, DateTimeUtils.nowDateTime());

        approvalTemplate = approvalTemplateRepository.save(approvalTemplate);

        validateDuplicatedApprovalTemplateLineSequence(requestDTO.getLines());

        final List<ApprovalTemplateLineResponseDTO> approvalTemplateLines =
                saveApprovalTemplateLines(approvalTemplate, requestDTO.getLines()).stream()
                        .map(ApprovalTemplateLineResponseDTO::of)
                        .collect(Collectors.toList());

        final List<ApprovalTemplateLineResponseDTO> approvalLines = approvalTemplateLines.stream()
                .filter(dto -> APPROVAL_TYPE_APPROVAL.equals(dto.getLineType()) ||
                        APPROVAL_TYPE_REVIEWER.equals(dto.getLineType()))
                .sorted(Comparator.comparingInt(ApprovalTemplateLineResponseDTO::getSequence))
                .collect(Collectors.toList());

        final List<ApprovalTemplateLineResponseDTO> referenceLines = approvalTemplateLines.stream()
                .filter(dto -> APPROVAL_TYPE_REFERENCE.equals(dto.getLineType()))
                .collect(Collectors.toList());

        final List<ApprovalTemplateLineResponseDTO> recipientLines = approvalTemplateLines.stream()
                .filter(dto -> APPROVAL_TYPE_RECIPIENT.equals(dto.getLineType()))
                .collect(Collectors.toList());

        return ApprovalTemplateResponseDTO.of(approvalTemplate, approvalLines, referenceLines, recipientLines);
    }

    private List<ApprovalTemplateLine> saveApprovalTemplateLines(final ApprovalTemplate approvalTemplate,
                                                                 final List<ApprovalTemplateLineCreateRequestDTO> requestDTOS) {
        final List<Integer> approverIds = requestDTOS.stream()
                .map(ApprovalTemplateLineCreateRequestDTO::getApproverId)
                .collect(Collectors.toList());

        final Map<Integer, Employee> approverMap = employeeRepository.findByIdIn(approverIds).stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));

        final List<ApprovalTemplateLine> approvalTemplateLines = new ArrayList<>();

        requestDTOS.forEach(requestDTO -> {
            final Employee employee = approverMap.get(requestDTO.getApproverId());

            if (employee == null) {
                throw new EmployeeNotFoundException();
            }

            validateApprovalTemplateLineSequence(requestDTO);

            final ApprovalTemplateLine approvalTemplateLine = new ApprovalTemplateLine(requestDTO.getSequence(),
                    requestDTO.getLineType(), requestDTO.getNote(), approvalTemplate, employee);

            approvalTemplateLines.add(approvalTemplateLine);
        });

        return approvalTemplateLineRepository.saveAll(approvalTemplateLines);
    }

    private void validateApprovalTemplateName(final Employee employee, final String name) {
        if (approvalTemplateRepository.existsByEmployeeAndName(employee, name)) {
            throw new ApprovalTemplateNameDuplicatedException();
        }
    }

    private void validateApprovalTemplateLineSequence(final ApprovalTemplateLineCreateRequestDTO requestDTO) {
        final String lineType = requestDTO.getLineType();

        if ((lineType.equals(APPROVAL_TYPE_APPROVAL) || lineType.equals(APPROVAL_TYPE_REVIEWER))
                && requestDTO.getSequence() == null) {
            throw new ApprovalLineSequenceRequiredException();
        }

        if ((lineType.equals(APPROVAL_TYPE_RECIPIENT) || lineType.equals(APPROVAL_TYPE_REFERENCE))
                && requestDTO.getSequence() != null) {
            throw new ApprovalLineSequenceNotAllowedException();
        }
    }

    private void validateDuplicatedApprovalTemplateLineSequence(final List<ApprovalTemplateLineCreateRequestDTO> requestDTOs) {
        final Set<Integer> sequenceSet = new HashSet<>();

        for (ApprovalTemplateLineCreateRequestDTO dto : requestDTOs) {
            final String lineType = dto.getLineType();

            if (APPROVAL_TYPE_APPROVAL.equals(lineType) || APPROVAL_TYPE_REVIEWER.equals(lineType)) {
                if (!sequenceSet.add(dto.getSequence())) {
                    throw new ApprovalLineSequenceDuplicatedException();
                }
            }
        }
    }
}