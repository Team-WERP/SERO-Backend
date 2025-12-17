package com.werp.sero.security.principal;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.enums.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final Employee employee;
    private final ClientEmployee clientEmployee;
    private final List<String> permissions;
    private final Type type;

    public CustomUserDetails(final Employee employee, final List<String> permissions) {
        this.employee = employee;
        this.permissions = permissions;
        this.clientEmployee = null;
        this.type = Type.EMPLOYEE;
    }

    public CustomUserDetails(final ClientEmployee clientEmployee, final List<String> permissions) {
        this.employee = null;
        this.clientEmployee = clientEmployee;
        this.permissions = permissions;
        this.type = Type.CLIENT_EMPLOYEE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();

        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        return authorities;
    }

    @Override
    public String getPassword() {
        return (this.type == Type.EMPLOYEE) ? this.employee.getPassword() : this.clientEmployee.getPassword();
    }

    @Override
    public String getUsername() {
        return (this.type == Type.EMPLOYEE) ? this.employee.getEmail() : this.clientEmployee.getEmail();
    }

    public Type getType() {
        return type;
    }

    public ClientEmployee getClientEmployee() {
        return clientEmployee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isEmployee() {
        return (this.type == Type.EMPLOYEE) ? true : false;
    }
}