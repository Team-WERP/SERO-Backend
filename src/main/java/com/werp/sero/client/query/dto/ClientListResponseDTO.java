package com.werp.sero.client.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientListResponseDTO {
    private Integer id;
    private String companyName;
    private String address;
    private String managerName;
    private String managerContact;
    private Integer creditLimit;
    private Integer receivables;
    private Integer unpaidAmount; // creditLimit - receivables
}
