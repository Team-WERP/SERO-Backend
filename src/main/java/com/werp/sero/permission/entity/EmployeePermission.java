package com.werp.sero.permission.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "employee_permission")
@NoArgsConstructor
@Entity
public class EmployeePermission {
    @EmbeddedId
    private EmployeePermissionId id;
}