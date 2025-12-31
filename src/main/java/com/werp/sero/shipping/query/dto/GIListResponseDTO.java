package com.werp.sero.shipping.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "출고지시 목록 응답 DTO")
public class GIListResponseDTO {

    @Schema(description = "출고지시 ID")
    private int id;

    @Schema(description = "출고지시 번호")
    private String giCode;

    @Schema(description = "주문 번호")
    private String soCode;

    @Schema(description = "납품서 번호")
    private String doCode;

    @Schema(description = "품목명 (첫번째 품목)")
    private String itemName;

    @Schema(description = "품목 개수")
    private int itemCount;

    @Schema(description = "창고명")
    private String warehouseName;

    @Schema(description = "출고지시 일시")
    private String createdAt;

    @Schema(description = "납기 일시")
    private String shippedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "요청자 (영업팀)")
    private String requesterName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "담당자 (물류팀)")
    private String managerName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "고객사 담당자")
    private String clientManagerName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "고객사명")
    private String clientName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "배송 메모")
    private String deliveryNote;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "배송지 주소")
    private String clientAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "수령인")
    private String recipientName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "수령인 연락처")
    private String recipientContact;

    @Schema(description = "상태")
    private String status;

    @Schema(description = "문서 url")
    private String giUrl;
}
