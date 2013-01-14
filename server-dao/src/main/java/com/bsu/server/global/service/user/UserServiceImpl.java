package com.bsu.server.global.service.user;

import com.bsu.server.assembler.RoleAssembler;
import com.bsu.server.assembler.UserAssembler;
import com.bsu.server.controller.RoleController;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.dto.security.UserRole;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UserController userController;
    @Autowired
    private RoleController roleController;

    @Override
    public List<UserDto> getUsersByRoles(Set<RoleDto> roles) {
        Set<String> roleNames = new HashSet<String>();
        for (RoleDto roleDto : roles) {
            roleNames.add(roleDto.getName());
        }
        List<UserAccount> users = userController.getUsersByRoles(roleNames);
        List<UserDto> result = new ArrayList<>();
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
        UserAccount newUser = UserAssembler.assemble(user);
        newUser.setPassword("123");
        return UserAssembler.assemble(userController.createUser(newUser));
    }

    @Override
    public UserDto update(UserDto user) {
        return null;
    }

    @Override
    public void delete(Integer userId) {
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserAccount> users = userController.getUsers();
        List<UserDto> result = Lists.transform(users, new Function<UserAccount, UserDto>() {
            @Override
            public UserDto apply(UserAccount input) {
                return UserAssembler.assemble(input);
            }
        });
        return result;
    }

    @Override
    public List<RoleDto> getRoles() {
        List<UserRole> dbRoles = roleController.getRoles();
       return RoleAssembler.assemble(dbRoles);
    }

    @Override
    public UserDetails getUserByUserName(String username) {
        return userController.getUserByUsername(username);
    }
}
