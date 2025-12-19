package com.werp.sero.production.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PRDraftDetailResponseDTO {
    private int prId;
    private int soId;
    private String soCode;

    private String status;
    private String dueAt;
    private String reason;

    private int totalQuantity;
    private String createdAt;

    private List<PRDraftItemResponseDTO> items;
}
