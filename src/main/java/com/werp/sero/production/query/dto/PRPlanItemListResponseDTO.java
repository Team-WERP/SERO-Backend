package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PRPlanItemListResponseDTO {
    private int prId;
    private String prCode;
    private String status;
    private List<PRPlanItemResponseDTO> items;
}
