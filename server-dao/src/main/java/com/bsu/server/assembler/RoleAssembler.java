package com.bsu.server.assembler;

import com.bsu.server.dto.security.UserRole;
import com.bsu.service.api.global.admin.dto.RoleDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 27.12.12
 *         Time: 20.34
 */
public class RoleAssembler {
    public static RoleDto assemble(UserRole role) {
        RoleDto dto = new RoleDto();
        dto.setName(role.getRoleName());
        dto.setId(role.getId().toString());
        return dto;
    }

    public static UserRole assemble(RoleDto roleDto) {
        UserRole role = new UserRole();
        role.setId(roleDto.getId() == null ? null : Integer.parseInt(roleDto.getId()));
        role.setRoleName(roleDto.getName());
        return role;
    }

    public static List<RoleDto> assemble(List<UserRole> dbRoles) {
        if (dbRoles == null) {
            return null;
        }
        List<RoleDto> roles = new ArrayList<>();
        for (UserRole role : dbRoles){
            roles.add(assemble(role));
        }
        return roles;
    }
}
