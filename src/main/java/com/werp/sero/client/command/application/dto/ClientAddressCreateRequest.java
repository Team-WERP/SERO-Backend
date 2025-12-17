package com.werp.sero.client.command.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientAddressCreateRequest {
    private String name;
    private String address;
    private String recipientName;
    private String recipientContact;
    private boolean isDefault;
}
