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
        private int ppId;
        private int quantity;
    }
}
