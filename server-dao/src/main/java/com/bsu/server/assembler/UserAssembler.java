package com.bsu.server.assembler;

import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.dto.security.UserRole;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
@Service
public class UserAssembler {
    @Autowired
    private UserController userController;

    public UserAccount assemble(UserDto userDto) {
        UserAccount entity = new UserAccount();
        if (userDto.getUserId() != null) {
            entity = userController.getById(userDto.getUserId());
        }
        entity.setId(userDto.getUserId());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setMiddleName(userDto.getLastName());
        entity.setUsername(userDto.getUsername());
        if (userDto.getRoles() != null) {
            Set<UserRole> roles = Sets.newHashSet(RoleAssembler.assemble(userDto.getRoles()));
            entity.setUserRoles(roles);
        }
        //TODO: increase this!
        return entity;
    }

    public UserDto assemble(UserAccount userAccount) {
        UserDto dto = new UserDto();
        dto.buildUserId(userAccount.getId()).buildFirstName(userAccount.getFirstName())
                .buildLastName(userAccount.getLastName()).buildMiddleName(userAccount.getMiddleName())
                .buildUsername(userAccount.getUsername());
        Set<RoleDto> assignedRoles = new HashSet<>();
        for (UserRole role : userAccount.getUserRoles()) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId() + "");
            roleDto.setName(role.getRoleName());
            assignedRoles.add(roleDto);
        }
        dto.buildRoles(assignedRoles.isEmpty() ? null : assignedRoles.iterator().next());
        return dto;
    }
}
