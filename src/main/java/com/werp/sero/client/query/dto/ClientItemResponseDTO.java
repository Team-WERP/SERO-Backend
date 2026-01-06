package com.werp.sero.client.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientItemResponseDTO {
    private Integer id;
    private Integer itemId;           // material의 ID (A.item_id)
    private String itemCode;
    private String itemName;
    private String spec;
    private String unit;
    private Integer unitPrice;        // 기본 단가 (material 테이블)
    private Integer contractPrice;     // 고객사 단가 (client_item 테이블)
    private Integer moq;
    private String status;
}
