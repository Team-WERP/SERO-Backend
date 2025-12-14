package com.werp.sero.security.dto;

import com.werp.sero.security.dto.enums.Type;
import com.werp.sero.client.entity.ClientEmployee;
import com.werp.sero.employee.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Employee employee;
    private ClientEmployee clientEmployee;
    private final List<String> permissions;
    private Type type;

    public CustomUserDetails(final Employee employee, final List<String> permissions) {
        this.employee = employee;
        this.permissions = permissions;
        this.type = Type.EMPLOYEE;
    }

    public CustomUserDetails(final ClientEmployee clientEmployee, final List<String> permissions) {
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
        return (type == Type.EMPLOYEE) ? employee.getPassword() : clientEmployee.getPassword();
    }

    @Override
    public String getUsername() {
        return (type == Type.EMPLOYEE) ? employee.getEmail() : clientEmployee.getEmail();
    }
}