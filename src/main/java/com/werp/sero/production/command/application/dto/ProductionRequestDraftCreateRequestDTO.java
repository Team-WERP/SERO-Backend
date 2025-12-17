package com.werp.sero.production.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductionRequestDraftCreateRequestDTO {
    private int soId;
    private String dueAt;
    private String reason;
    List<ProductionRequestItemCreateRequestDTO> items;
}
