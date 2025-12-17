package com.werp.sero.production.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductionRequestItemCreateRequestDTO {
    private int soItemId;
    private int quantity;
}
