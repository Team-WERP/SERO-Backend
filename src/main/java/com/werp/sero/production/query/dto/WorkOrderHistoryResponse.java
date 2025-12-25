package com.werp.sero.production.query.dto;

import com.werp.sero.production.command.domain.aggregate.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderHistoryResponse {
    private Action action;
    private String actedAt;
    private String note;
}
