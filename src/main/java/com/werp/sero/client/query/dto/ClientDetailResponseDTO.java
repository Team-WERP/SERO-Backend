package com.werp.sero.client.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetailResponseDTO {
    // 기본 정보
    private Integer id;
    private String companyName;
    private String businessNumber;
    private String businessType;
    private String businessItem;
    private String ceoName;
    private String phoneNumber;

    // 담당자 정보
    private String managerName;
    private String managerContact;
    private String managerEmail;

    // 여신 정보
    private Integer creditLimit;
    private Integer receivables;
    private Integer unpaidAmount; // creditLimit - receivables

    // 배송지 정보
    private List<ClientAddressResponseDTO> addresses;

    // 거래 품목 정보
    private List<ClientItemResponseDTO> items;
}
