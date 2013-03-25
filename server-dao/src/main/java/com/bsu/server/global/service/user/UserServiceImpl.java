package com.bsu.server.global.service.user;

import com.bsu.server.assembler.RoleAssembler;
import com.bsu.server.assembler.UserAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.RoleController;
import com.bsu.server.controller.UserController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.dto.security.UserRole;
import com.bsu.server.global.service.base.BaseSearchableServiceImpl;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
@Service
public class UserServiceImpl extends BaseSearchableServiceImpl<UserDto, UserAccount> implements UserService {

    @Autowired
    private UserController userController;
    @Autowired
    private RoleController roleController;
    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private RoleAssembler roleAssembler;
    @Autowired
    private transient PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsersByRoles(Set<RoleDto> roles) {
        Set<String> roleNames = new HashSet<>();
        for (RoleDto roleDto : roles) {
            roleNames.add(roleDto.getName());
        }
        List<UserAccount> users = userController.getUsersByRoles(roleNames);
        return convertList(users);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserAccount> users = userController.getUsers();
        return convertList(users);
    }

    @Override
    public List<RoleDto> getRoles() {
        List<UserRole> dbRoles = roleController.getList();
        return roleAssembler.assemble(dbRoles);
    }

    @Override
    public UserDetails getUserByUserName(String username) {
        return userController.getUserByUsername(username);
    }

    @Override
    protected BaseController<UserAccount> getController() {
        return userController;
    }

    @Override
    protected BaseConverter<UserDto, UserAccount> getConverter() {
        return userAssembler;
    }
}
