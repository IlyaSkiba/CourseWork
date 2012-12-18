package com.bsu.service.api.global.admin;

import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;

import java.util.List;
import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
public interface UserService {

    List<UserDto> getTeachers();

    List<UserDto> getUsersByRoles(Set<RoleDto> roles);

    UserDto get(Integer userId);

    UserDto create(UserDto user);

    UserDto update(UserDto user);

    void delete(Integer userId);
}
