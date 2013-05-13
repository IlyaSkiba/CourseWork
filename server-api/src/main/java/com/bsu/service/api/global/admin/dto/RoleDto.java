package com.bsu.service.api.global.admin.dto;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author Ilya SKiba
 * @created 16/11/12
 */
public class RoleDto implements Serializable, GrantedAuthority {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
