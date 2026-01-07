package com.werp.sero.client.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPortalDetailResponseDTO {
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
    private long creditLimit;
    private long receivables;
    private long unpaidAmount;
}
