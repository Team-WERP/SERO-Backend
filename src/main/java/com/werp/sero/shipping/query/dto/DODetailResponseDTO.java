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
@Schema(description = "납품서 상세 조회 응답 DTO")
public class DODetailResponseDTO {

    @Schema(description = "납품서 문서번호", example = "DO-20251219-001")
    private String doCode;

    @Schema(description = "납품서 PDF URL", example = "https://sero-bucket.s3.ap-northeast-2.amazonaws.com/delivery-orders/DO-20251219-001.pdf")
    private String doUrl;

    @Schema(description = "일자 (납품서 생성일자)", example = "2025-12-09")
    private String createdAt;

    @Schema(description = "수신처 (고객사명 + 담당자)", example = "현대모비스 자재팀 김민지 대리")
    private String recipient;

    @Schema(description = "상호 (고객사명)", example = "현대모비스")
    private String companyName;

    @Schema(description = "대표자 (고객사 대표)", example = "정의선, 이규석")
    private String ceoName;

    @Schema(description = "사업자번호", example = "101-81-16406")
    private String businessNo;

    @Schema(description = "전화번호", example = "1588-7278")
    private String companyContact;

    @Schema(description = "주소 (배송지)", example = "서울특별시 강남구 테헤란로 203 (역삼동, SIE타워)")
    private String address;

    @Schema(description = "업태", example = "제조업")
    private String businessType;

    @Schema(description = "업종", example = "자동차용 신품부품 제조업")
    private String businessItem;

    @Schema(description = "합계금액", example = "2300000000")
    private long totalAmount;

    @Schema(description = "납기일시 (납품서 납기일)", example = "2025-12-25 14:00")
    private String shippedAt;

    @Schema(description = "납품장소 (배송지 주소)", example = "경상북도 경주시 명계3일반산업")
    private String deliveryLocation;

    @Schema(description = "특이사항", example = "파손 주의")
    private String note;

    @Schema(description = "납품 품목 목록")
    private List<DODetailItemDTO> items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "납품서 품목 상세")
    public static class DODetailItemDTO {

        @Schema(description = "품목코드", example = "MC-A01")
        private String itemCode;

        @Schema(description = "품명", example = "모터코어 A")
        private String itemName;

        @Schema(description = "규격", example = "3*35*24mm")
        private String spec;

        @Schema(description = "수량 (납품수량)", example = "1000")
        private int quantity;

        @Schema(description = "단위", example = "EA")
        private String unit;

        @Schema(description = "단가", example = "2300000")
        private int unitPrice;

        @Schema(description = "금액 (단가 * 수량)", example = "2300000000")
        private long amount;
    }
}
