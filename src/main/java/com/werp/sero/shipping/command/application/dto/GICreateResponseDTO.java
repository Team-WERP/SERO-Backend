package com.werp.sero.shipping.command.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GICreateResponseDTO {
    private final String message;
    private final int id;
    private final String giCode;
}
