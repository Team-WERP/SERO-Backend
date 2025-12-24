package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WOByPPResponseDTO {
    private int woId;
    private String woCode;
    private String workDate;
    private int quantity;

    private int ppId;
    private int prId;

    private String ppCode;
    private String prCode;

    private String status;
}
