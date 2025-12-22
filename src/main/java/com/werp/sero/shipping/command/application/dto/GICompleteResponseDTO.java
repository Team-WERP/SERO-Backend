package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GICompleteResponseDTO {

    @Schema(description = "출고지시 코드", example = "GI-20251220-001")
    private String giCode;

    @Schema(description = "출고 창고명", example = "중앙창고")
    private String warehouseName;

    @Schema(description = "출고 처리 시각", example = "2025-12-20 14:30")
    private String completedAt;

    @Schema(description = "운송장 번호", example = "SERO-20251222-D001")
    private String trackingNumber;

    @Schema(description = "배송기사 이름", example = "김기사")
    private String driverName;

    @Schema(description = "배송기사 연락처", example = "010-1234-5678")
    private String driverContact;

    @Schema(description = "출고 품목 상세")
    private List<GICompleteItemDTO> items;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GICompleteItemDTO {

        @Schema(description = "품목 코드", example = "MC-A01")
        private String itemCode;

        @Schema(description = "품목명", example = "모터코어A")
        private String itemName;

        @Schema(description = "규격", example = "100x200")
        private String spec;

        @Schema(description = "출고 수량", example = "100")
        private int quantity;

        @Schema(description = "단위", example = "EA")
        private String unit;

        @Schema(description = "출고 후 창고 잔여 재고", example = "400")
        private int remainingStock;
    }
}
