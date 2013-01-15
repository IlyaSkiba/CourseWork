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
    @Autowired
    private UserAssembler userAssembler;

    @Override
    public List<UserDto> getUsersByRoles(Set<RoleDto> roles) {
        Set<String> roleNames = new HashSet<>();
        for (RoleDto roleDto : roles) {
            roleNames.add(roleDto.getName());
        }
        List<UserAccount> users = userController.getUsersByRoles(roleNames);
        List<UserDto> result = new ArrayList<>();
        for (UserAccount user : users) {
            result.add(userAssembler.assemble(user));
        }
        return result;
    }

    @Override
    public UserDto get(Integer userId) {
        return userAssembler.assemble(userController.getUser(userId));
    }

    @Override
    public UserDto create(UserDto user) {
        UserAccount newUser = userAssembler.assemble(user);
        newUser.setPassword("123");
        return userAssembler.assemble(userController.createUser(newUser));
    }

    @Override
    public UserDto update(UserDto user) {
        UserAccount newUser = userAssembler.assemble(user);
        return userAssembler.assemble(userController.update(newUser));
    }

    @Override
    public void delete(Integer userId) {
        userController.delete(userId);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserAccount> users = userController.getUsers();
        List<UserDto> result = Lists.transform(users, new TranformFunction());
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

    private class TranformFunction implements Function<UserAccount, UserDto> {
        @Override
        public UserDto apply(UserAccount input) {
            return userAssembler.assemble(input);
        }
    }
}
