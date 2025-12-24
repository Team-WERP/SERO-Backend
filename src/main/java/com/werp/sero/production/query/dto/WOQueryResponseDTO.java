package com.werp.sero.production.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WOQueryResponseDTO {
    private int woId;
    private String woCode;
    private String workDate;
    private int quantity;

    private int ppId;
    private String ppCode;

    private int prId;
    private String prCode;

    private String lineName;
}
