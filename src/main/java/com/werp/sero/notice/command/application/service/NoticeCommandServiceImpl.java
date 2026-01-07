package com.werp.sero.notice.command.application.service;

import com.werp.sero.code.exception.CommonCodeNotFoundException;
import com.werp.sero.code.query.dto.CommonCodeDetailManageDTO;
import com.werp.sero.code.query.service.CommonCodeManageQueryService;
import com.werp.sero.common.file.FileValidator;
import com.werp.sero.common.file.S3Uploader;
import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.exception.EmployeeNotFoundException;
import com.werp.sero.notice.command.application.dto.NoticeAttachmentResponseDTO;
import com.werp.sero.notice.command.application.dto.NoticeCreateRequestDTO;
import com.werp.sero.notice.command.application.dto.NoticeResponseDTO;
import com.werp.sero.notice.command.domain.aggregate.Notice;
import com.werp.sero.notice.command.domain.aggregate.NoticeAttachment;
import com.werp.sero.notice.command.domain.repository.NoticeAttachmentRepository;
import com.werp.sero.notice.command.domain.repository.NoticeRepository;
import com.werp.sero.notice.exception.NoticeAccessDeniedException;
import com.werp.sero.notice.exception.NoticeNotFoundException;
import com.werp.sero.permission.command.domain.repository.EmployeePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeCommandServiceImpl implements NoticeCommandService {
    private final static String CODE = "NOTICE_TYPE";

    private final NoticeRepository noticeRepository;
    private final NoticeAttachmentRepository noticeAttachmentRepository;
    private final S3Uploader s3Uploader;
    private final FileValidator fileValidator;
    private final EmployeePermissionRepository employeePermissionRepository;
    private final EmployeeRepository employeeRepository;

    private final CommonCodeManageQueryService commonCodeManageQueryService;

    @Transactional
    @Override
    public NoticeResponseDTO registerNotice(Employee employee, final NoticeCreateRequestDTO requestDTO,
                                            final List<MultipartFile> files) {
        employee = employeeRepository.findById(employee.getId()).orElseThrow(EmployeeNotFoundException::new);

        validateCategory(requestDTO.getCategory());

        Notice notice = new Notice(requestDTO.getTitle(), requestDTO.getContent(), requestDTO.getCategory(),
                (requestDTO.isPinned() || requestDTO.isEmergency() ? DateTimeUtils.nowDate() : null),
                requestDTO.getPinnedEndAt(), requestDTO.isEmergency(), DateTimeUtils.nowDateTime(), employee);

        notice = noticeRepository.save(notice);

        List<NoticeAttachmentResponseDTO> attachments = new ArrayList<>();

        if (files != null && !files.isEmpty()) {
            attachments = saveFiles(notice, files).stream()
                    .map(NoticeAttachmentResponseDTO::of)
                    .collect(Collectors.toList());
        }

        return NoticeResponseDTO.of(notice, attachments);
    }

    @Transactional
    @Override
    public void deleteNotice(final Employee employee, final int noticeId) {
        final Notice notice = findById(noticeId);

        validateNoticeAccess(employee, notice);

        noticeAttachmentRepository.findByNoticeId(noticeId).forEach(
                attachment -> s3Uploader.delete(attachment.getUrl())
        );

        noticeAttachmentRepository.deleteByNoticeId(noticeId);

        noticeRepository.delete(notice);
    }

    private void validateNoticeAccess(final Employee employee, final Notice notice) {
        final List<String> permissions = employeePermissionRepository.findPermissionCodeByEmployee(employee);

        if (!permissions.contains("AC_SYS") && notice.getEmployee().getId() != employee.getId()) {
            throw new NoticeAccessDeniedException();
        }
    }

    private Notice findById(final int noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);
    }

    private void validateCategory(final String category) {
        if (category == null || category.isEmpty() || category.isBlank()) {
            throw new CommonCodeNotFoundException();
        }

        final List<String> noticeTypes = commonCodeManageQueryService.getCodeDetailsByType(CODE).stream()
                .map(CommonCodeDetailManageDTO::getCode).collect(Collectors.toList());

        if (!noticeTypes.contains(category)) {
            throw new CommonCodeNotFoundException();
        }
    }

    private List<NoticeAttachment> saveFiles(final Notice notice, final List<MultipartFile> files) {
        final List<NoticeAttachment> noticeAttachments = new ArrayList<>();

        files.forEach(file -> {
            fileValidator.validateImageOrDocument(file);

            final String url = s3Uploader.upload("sero/documents/", file);

            final NoticeAttachment noticeAttachment = new NoticeAttachment(file.getOriginalFilename(), url, notice);

            noticeAttachments.add(noticeAttachment);
        });

        return noticeAttachmentRepository.saveAll(noticeAttachments);
    }
}