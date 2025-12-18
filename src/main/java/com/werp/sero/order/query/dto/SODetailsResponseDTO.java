package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SODetailsResponseDTO {
    @Schema(description = "주문 id")
    private int orderId;

    @Schema(description = "주문일시")
    private String orderedAt;

    @Schema(description = "납기일시")
    private String shippedAt;

    @Schema(description = "비고")
    private String note;

    @Schema(description = "고객사 이름")
    private String clientName;

    @Schema(description = "고객사 담당자명")
    private String clientManagerName;

    @Schema(description = "고객사 담당자 연락처")
    private String clientManagerContact;

    @Schema(description = "여신한도")
    private long creditLimit;

    @Schema(description = "주문 가능 금액")
    private long availablePrice;

    @Schema(description = "주문 금액")
    private long totalPrice;

    @Schema(description = "주문 반영 후 가능 금액")
    private long remainingAmount;
    
}
