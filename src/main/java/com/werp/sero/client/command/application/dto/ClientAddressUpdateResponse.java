package com.werp.sero.client.command.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientAddressUpdateResponse {
    private int id;
    private String name;
    private String address;
    private String recipientName;
    private String recipientContact;

    @JsonProperty("isDefault")
    private boolean isDefault;
}
