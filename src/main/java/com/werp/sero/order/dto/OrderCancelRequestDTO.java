package com.werp.sero.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 취소 요청 DTO")
public class OrderCancelRequestDTO {
    @NotBlank(message = "취소 사유는 필수 입력 항목입니다.")
    @Schema(description = "주문 취소 사유")
    private String rejectionReason;
}
