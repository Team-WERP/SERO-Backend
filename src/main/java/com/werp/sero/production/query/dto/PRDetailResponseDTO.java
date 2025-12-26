package com.werp.sero.production.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PRDetailResponse")
public class PRDetailResponseDTO {
    private Header header;
    private List<Item> items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        private int prId;
        private String prCode;
        private String soCode;
        private String status;
        private String productionProgress;
        private String requestedAt;
        private String dueAt;
        private String drafterName;
        private Integer managerId;
        private String managerName;
        private int totalQuantity;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private int prItemId;
        private int soItemId;
        private String itemCode;
        private String itemName;
        private String spec;
        private String unit;
        private int requestedQuantity;
        private String status;
    }
}
