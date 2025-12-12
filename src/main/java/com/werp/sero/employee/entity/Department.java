package com.werp.sero.employee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "department")
@NoArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dept_code", nullable = false)
    private String deptCode;

    @Column(name = "dept_name", nullable = false)
    private String deptName;
}