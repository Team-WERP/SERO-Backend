package com.werp.sero.deadline.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "납기 가능 여부 조회 응답 DTO")
public class DeadLineQueryResponseDTO {

    @Schema(description = "자재 코드")
    private String materialCode;

    @Schema(description = "희망 납기일 (yyyy-MM-dd HH:mm)")
    private String desiredDeliveryDate;

    @Schema(description = "실제 가능 납기일 (ETA) (yyyy-MM-dd HH:mm)")
    private String expectedDeliveryDate;

    @Schema(description = "희망 납기일 가능 여부")
    private boolean deliverable;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "오류 메시지 (생산 불가 시)")
    private String errorMessage;
}
