package com.werp.sero.notice.command.application.dto;

import com.werp.sero.notice.command.domain.aggregate.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDTO {
    private int noticeId;

    private String category;

    private String title;

    private String content;

    private int authorId;

    private String authorName;

    private String authorRank;

    private String authorPosition;

    private String authorDepartmentName;

    private String pinnedStartAt;

    private String pinnedEndAt;

    private boolean isEmergency;

    private String createdAt;

    private String updatedAt;

    private List<NoticeAttachmentResponseDTO> attachments;

    public static NoticeResponseDTO of(final Notice notice, final List<NoticeAttachmentResponseDTO> attachments) {
        return NoticeResponseDTO.builder()
                .noticeId(notice.getId())
                .category(notice.getCategory())
                .title(notice.getTitle())
                .content(notice.getContent())
                .authorId(notice.getEmployee().getId())
                .authorName(notice.getEmployee().getName())
                .authorRank(notice.getEmployee().getRankCode())
                .authorPosition(notice.getEmployee().getPositionCode())
                .authorDepartmentName((notice.getEmployee().getDepartment() != null) ?
                        notice.getEmployee().getDepartment().getDeptName() : null)
                .pinnedStartAt(notice.getPinnedStartAt())
                .pinnedEndAt(notice.getPinnedEndAt())
                .isEmergency(notice.isEmergency())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .attachments(attachments)
                .build();
    }
}