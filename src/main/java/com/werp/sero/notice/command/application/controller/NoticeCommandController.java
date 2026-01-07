package com.werp.sero.notice.command.application.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notice.command.application.dto.NoticeCreateRequestDTO;
import com.werp.sero.notice.command.application.dto.NoticeResponseDTO;
import com.werp.sero.notice.command.application.service.NoticeCommandService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "공지사항 - Command", description = "공지사항 관련 API")
@RequestMapping("/notices")
@RequiredArgsConstructor
@RestController
public class NoticeCommandController {
    private final NoticeCommandService noticeCommandService;

    @Operation(summary = "공지사항 등록")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<NoticeResponseDTO> registerNotice(@CurrentUser final Employee employee,
                                                            @Valid @RequestPart(name = "requestDTO") final NoticeCreateRequestDTO requestDTO,
                                                            @RequestPart(name = "files", required = false) final List<MultipartFile> files) {
        final NoticeResponseDTO response = noticeCommandService.registerNotice(employee, requestDTO, files);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "공지사항 삭제")
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@CurrentUser final Employee employee,
                                             @PathVariable("noticeId") final int noticeId) {
        noticeCommandService.deleteNotice(employee, noticeId);

        return ResponseEntity.noContent().build();
    }
}
