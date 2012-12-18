package com.bsu.server.global.service.user;

import com.bsu.server.assembler.UserAssembler;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
@Service
public class UserServiceImpl implements UserService {
    private static final RoleDto TEACHER_ROLE = new RoleDto() {{
        setName("ROLE_TEACHER");
    }};
    @Autowired
    private UserController userController;

    @Override
    public List<UserDto> getTeachers() {
        Set<RoleDto> roles = new HashSet<>();
        roles.add(TEACHER_ROLE);
        return getUsersByRoles(roles);
    }

    @Override
    public List<UserDto> getUsersByRoles(Set<RoleDto> roles) {
        Set<String> roleNames = new HashSet<String>();
        for (RoleDto roleDto : roles) {
            roleNames.add(roleDto.getName());
        }
        List<UserAccount> users = userController.getUsersByRoles(roleNames);
        List<UserDto> result = new ArrayList<UserDto>();
        for (UserAccount user : users) {
            result.add(UserAssembler.assemble(user));
        }
        return result;
    }

    @Override
    public UserDto get(Integer userId) {
        return UserAssembler.assemble(userController.getUser(userId));
    }

    @Override
    public UserDto create(UserDto user) {
        return null;
    }

    @Override
    public UserDto update(UserDto user) {
        return null;
    }

    @Override
    public void delete(Integer userId) {
    }
}
