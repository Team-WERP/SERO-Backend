package com.werp.sero.production.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WOByPRResponseDTO {
    private int woId;
    private String woCode;
    private String workDate;
    private int quantity;

    private int ppId;
    private String ppCode;
    private String ppStatus;

    private int lineId;
    private String lineName;

    private int prId;
    private String prCode;
}
