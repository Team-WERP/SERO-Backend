package com.werp.sero.notice.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeCreateRequestDTO {
    @Schema(description = "제목", defaultValue = "시스템 점검 안내")
    @NotBlank
    private String title;

    @Schema(description = "내용",
            defaultValue = "보다 안정적인 서비스 제공을 위해 시스템 점검을 진행합니다. 점검 시간 동안 일부 기능 이용이 제한될 수 있습니다.")
    @NotBlank
    private String content;

    @Schema(description = "공지 구분", defaultValue = "NOTICE_INTERNAL")
    private String category;

    @Schema(description = "긴급 또는 고정 종료일", defaultValue = "2026-01-31")
    private String pinnedEndAt;

    @Schema(description = "고정 여부", defaultValue = "false")
    private boolean pinned;

    @Schema(description = "긴급 여부", defaultValue = "false")
    @NotNull
    private boolean emergency;
}