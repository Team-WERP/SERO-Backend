package com.werp.sero.code.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonCodeManageDTO {
    private String code;
    private String name;
    private String description;
    private int sortOrder;
}
