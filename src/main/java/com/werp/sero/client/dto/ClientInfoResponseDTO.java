package com.werp.sero.client.dto;

import com.werp.sero.client.entity.Client;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientInfoResponseDTO {

    private int id;
    private String companyName;        // 회사명
    private String ceoName;            // 대표자명
    private String companyContact;     // 대표전화
    private String businessNo;         // 사업자등록번호
    private String businessType;       // 업태
    private String businessItem;       // 종목
    private String address;            // 주소

    /* 설명. Entity -> DTO 변환 */
    public static ClientInfoResponseDTO from(Client client) {
        return ClientInfoResponseDTO.builder()
                .id(client.getId())
                .companyName(client.getCompanyName())
                .ceoName(client.getCeoName())
                .companyContact(client.getCompanyContact())
                .businessNo(client.getBusinessNo())
                .businessType(client.getBusinessType())
                .businessItem(client.getBusinessItem())
                .address(client.getAddress())
                .build();
    }
}
