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
    private List<ApprovalLine> approvalLines;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        private int prId;
        private int soId;
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
        private String reason;
        private Integer approvalId;
        private String approvalCode;
        private String drafterDepartment;
        private String drafterPosition;
        private String drafterRank;
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApprovalLine {
        private int sequence;
        private String approverName;
        private String approverDepartment;
        private String approverPosition;
        private String approverRank;
        private String status;
        private String lineType;
        private String processedAt;
        private String note;
    }
}
