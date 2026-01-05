package com.werp.sero.notice.command.application.dto;

import com.werp.sero.notice.command.domain.aggregate.NoticeAttachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeAttachmentResponseDTO {
    private int id;

    private String name;

    private String url;

    public static NoticeAttachmentResponseDTO of(final NoticeAttachment noticeAttachment) {
        return new NoticeAttachmentResponseDTO(noticeAttachment.getId(), noticeAttachment.getName(),
                noticeAttachment.getUrl());
    }
}