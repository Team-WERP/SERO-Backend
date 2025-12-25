package com.werp.sero.production.command.application.dto;

import lombok.Getter;

@Getter
public class WorkOrderCreateRequestDTO {
    private int ppId;
    private String workDate;
    private int quantity;
}
