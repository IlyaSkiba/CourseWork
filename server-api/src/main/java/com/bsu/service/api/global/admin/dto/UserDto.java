package com.bsu.service.api.global.admin.dto;

import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
public class UserDto {
    private Integer userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Set<RoleDto> roles;

    public Integer getUserId() {
        return userId;
    }

    public UserDto setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserDto setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public UserDto setRoles(Set<RoleDto> roles) {
        this.roles = roles;
        return this;
    }

}
