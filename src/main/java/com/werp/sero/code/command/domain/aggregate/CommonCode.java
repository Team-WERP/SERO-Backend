package com.werp.sero.code.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "common_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "CommonCodeManage")
public class CommonCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_code", nullable = false, length = 50)
    private String typeCode;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "parent_code", length = 50)
    private String parentCode;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(length = 255)
    private String description;

    @Column(name = "is_used", nullable = false, columnDefinition = "tinyint default 1")
    private boolean isUsed;

    @Builder
    public CommonCode(String typeCode, String code, String name, String parentCode,
                      int sortOrder, String description, boolean isUsed) {
        this.typeCode = typeCode;
        this.code = code;
        this.name = name;
        this.parentCode = parentCode;
        this.sortOrder = sortOrder;
        this.description = description;
        this.isUsed = isUsed;
    }

    public void update(String name, String parentCode, int sortOrder, String description, boolean isUsed) {
        this.name = name;
        this.parentCode = parentCode;
        this.sortOrder = sortOrder;
        this.description = description;
        this.isUsed = isUsed;
    }

    public void activate() {
        this.isUsed = true;
    }

    public void deactivate() {
        this.isUsed = false;
    }
}
