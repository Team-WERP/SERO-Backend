package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderCountDTO {
    private int total;
    private int running;
    private int paused;
    private int done;
}

