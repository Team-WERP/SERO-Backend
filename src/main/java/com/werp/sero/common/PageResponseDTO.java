package com.werp.sero.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDTO<T> {

    private List<T> content;
    private int page;
    private int size;
    private int totalCount;
    private int totalPages;
}
