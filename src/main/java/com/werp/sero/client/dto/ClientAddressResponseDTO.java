package com.werp.sero.client.dto;

import com.werp.sero.client.entity.ClientAddress;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientAddressResponseDTO {

    private int id;
    private String name;              // 배송지명
    private String address;           // 주소
    private Double latitude;          // 위도
    private Double longitude;         // 경도
    private String recipientName;     // 수령인명
    private String recipientContact;  // 수령인 연락처
    private boolean isDefault;        // 기본 배송지 여부

    /* 설명. Entity -> DTO 변환 */
    public static ClientAddressResponseDTO from(ClientAddress clientAddress) {
        return ClientAddressResponseDTO.builder()
                .id(clientAddress.getId())
                .name(clientAddress.getName())
                .address(clientAddress.getAddress())
                .latitude(clientAddress.getLatitude())
                .longitude(clientAddress.getLongitude())
                .recipientName(clientAddress.getRecipientName())
                .recipientContact(clientAddress.getRecipientContact())
                .isDefault(clientAddress.isDefault())
                .build();
    }
}
