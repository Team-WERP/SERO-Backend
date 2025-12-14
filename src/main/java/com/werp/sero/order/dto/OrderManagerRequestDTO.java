package com.werp.sero.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 담당자 배정 요청 DTO")
public class OrderManagerRequestDTO {
    @Schema(description = "본사 직원 id")
    private int empId;
}
