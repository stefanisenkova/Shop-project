package com.example.demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Employee employee;

    public CustomUserDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>authorities=new ArrayList<GrantedAuthority>();
        for(Role role:this.employee.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return  authorities;
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    public Long getId() {
        return employee.getId();
    }

    @Override
    public String getUsername() {
        return employee.getLastName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return employee.getFirstName() + " " + employee.getLastName();
    }
}
