package com.werp.sero.system.command.domain.aggregate;

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