package com.werp.sero.shipping.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "출고지시서 상세 조회 응답 DTO")
public class GIDetailResponseDTO {

    // 1. 기본 출고 지시 정보
    @Schema(description = "출고지시 ID (PK)", example = "1")
    private int giId;

    @Schema(description = "출고지시 번호", example = "GI-20251207-001")
    private String giCode;

    @Schema(description = "출고지시서 PDF URL", example = "https://sero-bucket.s3.ap-northeast-2.amazonaws.com/goods-issues/GI-20251207-001.pdf")
    private String giUrl;

    @Schema(description = "출고지시 일시", example = "2025-12-07 14:00")
    private String createdAt;

    @Schema(description = "출고 예정 일시", example = "2025-12-31 14:00")
    private String scheduledAt;

    @Schema(description = "출고 상태", example = "GI_RVW")
    private String status;

    // 2. 배송지 정보
    @Schema(description = "납품처 (고객사명)", example = "현대모비스")
    private String companyName;

    @Schema(description = "상세 주소", example = "서울특별시 강남구 테헤란로 203")
    private String address;

    @Schema(description = "수령인 이름", example = "김민지")
    private String recipientName;

    @Schema(description = "수령인 연락처", example = "010-1111-1111")
    private String recipientContact;

    // 3. 관련 주문 정보
    @Schema(description = "수주 ID (PK)", example = "1")
    private int soId;

    @Schema(description = "수주 번호", example = "SO-20251201-001")
    private String soCode;

    @Schema(description = "납품서 ID (PK)", example = "1")
    private int doId;

    @Schema(description = "납품서 번호", example = "DO-20251201-001")
    private String doCode;

    @Schema(description = "고객사 ID (PK)", example = "1")
    private int clientId;

    @Schema(description = "고객사명", example = "현대모비스")
    private String clientName;

    @Schema(description = "납기 일시", example = "2025-12-25 14:00")
    private String shippedAt;

    // 4. 출고 창고 정보
    @Schema(description = "출고 창고 ID (PK)", example = "1")
    private int warehouseId;

    @Schema(description = "출고 창고명", example = "서울 본사 창고")
    private String warehouseName;

    // 5. 특이사항
    @Schema(description = "특이사항", example = "파손 주의")
    private String note;

    // 6. 출고지시 품목 목록
    @Schema(description = "출고지시 품목 목록")
    private List<GIDetailItemDTO> items;

    // 7. 결재 정보 (담당자 배정 이후)
    @Schema(description = "결재 ID (PK)", example = "1")
    private Integer approvalId;

    @Schema(description = "결재 코드", example = "APV-20251207-001")
    private String approvalCode;

    @Schema(description = "기안자 ID (PK)", example = "1")
    private int drafterId;

    @Schema(description = "기안자명", example = "김철수")
    private String drafterName;

    @Schema(description = "기안자 부서", example = "물류재고관리부")
    private String drafterDepartment;

    @Schema(description = "기안자 직책", example = "과장")
    private String drafterPosition;

    @Schema(description = "기안자 직급", example = "사원")
    private String drafterRank;

    @Schema(description = "출고 담당자 ID (PK)", example = "2")
    private Integer managerId;

    @Schema(description = "출고 담당자명", example = "이영희")
    private String managerName;

    @Schema(description = "출고 담당자 부서", example = "물류재고관리부")
    private String managerDepartment;

    @Schema(description = "출고 담당자 직책", example = "과장")
    private String managerPosition;

    @Schema(description = "출고 담당자 직급", example = "대리")
    private String managerRank;

    @Schema(description = "결재선 목록")
    private List<GIApprovalLineDTO> approvalLines;

    // 8. 배송 정보
    @Schema(description = "운송장 번호", example = "SERO-20260102-001")
    private String trackingNumber;

    @Schema(description = "배송 기사명", example = "이동중")
    private String driverName;

    @Schema(description = "배송 기사 연락처", example = "010-2222-2222")
    private String driverContact;

    @Schema(description = "배송 출발 시간", example = "2026-01-02 17:27")
    private String departedAt;

    @Schema(description = "배송 도착 시간", example = "2026-01-16 17:00")
    private String arrivedAt;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "출고지시 결재선 정보")
    public static class GIApprovalLineDTO {

        @Schema(description = "결재자명", example = "김철수")
        private String approverName;

        @Schema(description = "결재자 부서", example = "물류재고관리부")
        private String approverDepartment;

        @Schema(description = "결재자 직책", example = "과장")
        private String approverPosition;

        @Schema(description = "결재자 직급", example = "사원")
        private String approverRank;

        @Schema(description = "결재 순서", example = "1")
        private Integer sequence;

        @Schema(description = "결재 상태", example = "ALS_APPR")
        private String status;

        @Schema(description = "결재 구분", example = "AT_APPR")
        private String lineType;

        @Schema(description = "결재 처리일시", example = "2025-12-07 15:30")
        private String processedAt;

        @Schema(description = "비고", example = "승인합니다")
        private String note;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "출고지시 품목 상세")
    public static class GIDetailItemDTO {

        @Schema(description = "출고지시 번호", example = "GI-20251207-001-001")
        private String giItemCode;

        @Schema(description = "품목 코드", example = "MC-A01")
        private String itemCode;

        @Schema(description = "품목명", example = "모터코어 A")
        private String itemName;

        @Schema(description = "규격", example = "3*35*24mm")
        private String spec;

        @Schema(description = "출고지시 수량(AUn)", example = "300")
        private int quantityAUn;

        @Schema(description = "출고지시 수량(BUn)", example = "600")
        private int quantityBUn;

        @Schema(description = "총 출고 수량", example = "900")
        private int totalQuantity;

        @Schema(description = "단위", example = "BOX")
        private String unit;
    }
}
