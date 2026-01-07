package com.werp.sero.client.command.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientItemUpdateRequest {

    @NotNull(message = "계약 단가는 필수입니다.")
    @Min(value = 0, message = "계약 단가는 0 이상이어야 합니다.")
    private Integer contractPrice;

    @NotBlank(message = "단가 변경 사유는 필수입니다.")
    private String reason;
}
