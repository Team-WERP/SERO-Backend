package com.werp.sero.permission.entity;

import com.werp.sero.employee.entity.Employee;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EmployeePermissionId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePermissionId that = (EmployeePermissionId) o;
        return Objects.equals(employee, that.employee) && Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, permission);
    }
}