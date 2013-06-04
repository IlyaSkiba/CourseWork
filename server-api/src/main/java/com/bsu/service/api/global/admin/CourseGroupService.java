package com.bsu.service.api.global.admin;

import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.global.admin.dto.UserGroupDto;

import java.util.List;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.45
 */

public interface CourseGroupService extends SearchableService<CourseGroupDto> {
    List<CourseGroupDto> getAssignedCourse(Integer courseId);

    void updateAssignation(List<CourseGroupDto> courses);

    List<CourseGroupDto> getCourses(UserGroupDto groupDto);

    List<UserDto> getUsersForCourse(CourseDto courseDto);
}
