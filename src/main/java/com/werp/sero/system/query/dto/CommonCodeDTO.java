package com.werp.sero.system.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonCodeDTO {
    private String code;
    private String name;
    private String description;
    private int sortOrder;
}
