package com.werp.sero.permission.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "menu")
@NoArgsConstructor
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "is_activated", nullable = false, columnDefinition = "TINYINT default 1")
    private boolean isActivated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parentMenu;
}