package com.bsu.service.api.global.admin;

import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.global.admin.dto.UserDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 08.10.12
 * Time: 12:05
 */
public interface CourseService extends SearchableService<CourseDto> {

    List<CourseDto> getCourses();

    List<CourseDto> searchByOwnerId(Integer id);

    List<CourseDto> loadCourseList(UserDto user);
}
