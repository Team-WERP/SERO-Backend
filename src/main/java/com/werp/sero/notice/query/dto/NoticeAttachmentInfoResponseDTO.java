package com.werp.sero.notice.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeAttachmentInfoResponseDTO {
    private int attachmentId;

    private String attachmentName;

    private String attachmentUrl;
}