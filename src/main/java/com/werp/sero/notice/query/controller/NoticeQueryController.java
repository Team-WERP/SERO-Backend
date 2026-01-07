package com.werp.sero.notice.query.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notice.query.dto.NoticeDetailResponseDTO;
import com.werp.sero.notice.query.dto.NoticeListResponseDTO;
import com.werp.sero.notice.query.service.NoticeQueryService;
import com.werp.sero.security.annotation.CurrentUser;
import com.werp.sero.security.principal.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공지사항 - Query", description = "공지사항 관련 조회 API")
@RequiredArgsConstructor
@RestController
public class NoticeQueryController {
    private final NoticeQueryService noticeQueryService;

    @Operation(summary = "본사 직원 전용 공지사항 상세 조회", description = "본사 직원 대상 공지사항의 상세 정보를 조회합니다.")
    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeDetailResponseDTO> getNoticeDetailInfo(@AuthenticationPrincipal final CustomUserDetails customUserDetails,
                                                                       @PathVariable("noticeId") final int noticeId) {
        final NoticeDetailResponseDTO responseDTO = noticeQueryService.getNoticeDetailInfo(customUserDetails, noticeId);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "고객사 직원 전용 공지사항 상세 조회", description = "고객사 직원 대상 공지사항의 상세 정보를 조회합니다.")
    @GetMapping("/clients/notices/{noticeId}")
    public ResponseEntity<NoticeDetailResponseDTO> getNoticeDetailInfoByClientEmployee(@AuthenticationPrincipal final CustomUserDetails customUserDetails,
                                                                                       @PathVariable("noticeId") final int noticeId) {
        final NoticeDetailResponseDTO responseDTO = noticeQueryService.getNoticeDetailInfo(customUserDetails, noticeId);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "본사 직원 전용 공지사항 목록 조회", description = "본사 직원 대상 공지사항 목록을 조회합니다.")
    @GetMapping("/notices")
    public ResponseEntity<NoticeListResponseDTO> getNotices(@CurrentUser final Employee employee,
                                                            @RequestParam(value = "category", required = false) final String category,
                                                            @RequestParam(value = "keyword", required = false) final String keyword,
                                                            @RequestParam(value = "onlyMine", defaultValue = "false") final boolean onlyMine,
                                                            @PageableDefault final Pageable pageable) {
        final NoticeListResponseDTO responseDTO = noticeQueryService.getNotices(employee, category, keyword, onlyMine, pageable);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "고객사 직원 전용 공지사항 목록 조회", description = "고객사 직원 대상 공지사항 목록을 조회합니다.")
    @GetMapping("/clients/notices")
    public ResponseEntity<NoticeListResponseDTO> getNoticesByClientEmployee(@RequestParam(value = "keyword", required = false) final String keyword,
                                                                            @PageableDefault final Pageable pageable) {
        final NoticeListResponseDTO responseDTO = noticeQueryService.getNoticesByClientEmployee(keyword, pageable);

        return ResponseEntity.ok(responseDTO);
    }
}