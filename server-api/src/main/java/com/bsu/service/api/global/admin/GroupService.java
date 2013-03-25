package com.bsu.service.api.global.admin;

import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.global.admin.dto.UserGroupDto;

import java.util.List;

/**
 * @author HomeUser
 *         Date: 27.1.13
 *         Time: 13.52
 */
public interface GroupService extends SearchableService<UserGroupDto> {

    UserGroupDto createGroup(UserGroupDto dto, List<CourseGroupDto> courses);

    List<UserDto> getSelectedUsers(UserGroupDto group);
}
