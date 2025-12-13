package com.werp.sero.commoncode.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "common_code")
@NoArgsConstructor
@Entity
public class CommonCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    private String description;

    @Column(name = "is_used", nullable = false, columnDefinition = "tinyint default 1")
    private boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private CommonCodeType commonCodeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private CommonCode parentCommonCode;
}