package com.bsu.service.api.global.admin;

import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
public interface UserService {

    List<UserDto> getUsersByRoles(Set<RoleDto> roles);

    UserDto getById(Integer userId);

    UserDto createOrUpdate(UserDto user);

    void delete(UserDto user);

    List<UserDto> getUsers();

    List<RoleDto> getRoles();

    UserDetails getUserByUserName(String username);

    List<UserDto> search(Map<String, String> filters, String orderField, String orderDirection, int from,
                         int pageSize);

    int count(Map<String, String> filters);
}
