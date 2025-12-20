package com.werp.sero.deadline.command.domain.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class LineMaterial {
    private int id;
    private String createdAt;
    private int cycleTime;
}