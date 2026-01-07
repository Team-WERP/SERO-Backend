package com.werp.sero.notice.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDetailResponseDTO {
    private int noticeId;

    private String category;

    private String title;

    private String content;

    private int creatorId;

    private String creatorName;

    private String creatorRankCode;

    private String creatorPositionCode;

    private String creatorDepartmentName;

    private String pinnedStartAt;

    private String pinnedEndAt;

    private boolean isEmergency;

    private String createdAt;

    private String updatedAt;

    private List<NoticeAttachmentInfoResponseDTO> attachments;
}