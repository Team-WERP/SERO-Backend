package com.werp.sero.production.command.application.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WorkOrderCreateRequestDTO {
    private int lineId;
    private String workDate;

    private List<Item> items;

    @Getter
    public static class Item {
        private Integer ppId;      // nullable
        private Integer prItemId;  // nullable
        private int quantity;
        private Boolean emergency; // 표시용/정책용
    }
}
