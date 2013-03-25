package com.bsu.service.api.global.admin.dto;

import com.bsu.service.api.dto.base.BaseDto;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
public class UserDto extends BaseDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDto buildUsername(String username) {
        this.username = username;
        return this;
    }
}
