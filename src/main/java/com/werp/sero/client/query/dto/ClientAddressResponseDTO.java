package com.werp.sero.client.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientAddressResponseDTO {
    private int id;
    private String name;
    private String address;
    private String recipientName;
    private String recipientContact;
    private boolean isDefault;
    
}
