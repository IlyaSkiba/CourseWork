package com.bsu.service.api.global.admin.dto;

import com.bsu.service.api.dto.base.BaseDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
public class UserDto extends BaseDto implements UserDetails {
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String password;
    private RoleDto roles;


    public String getFirstName() {
        return firstName;
    }

    public UserDto buildFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserDto buildMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto buildLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RoleDto getRoles() {
        return roles;
    }

    public UserDto buildRoles(RoleDto roles) {
        this.roles = roles;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoles(RoleDto roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles != null) {
            HashSet<GrantedAuthority> res = new HashSet<>(1);
            res.add(roles);
            return res;
        }
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDto buildUsername(String username) {
        this.username = username;
        return this;
    }
}
