package com.bsu.service.api.global.admin;

import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.CourseDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 08.10.12
 * Time: 12:05
 */
public interface CourseService extends SearchableService<CourseDto> {
    /**
     * @param id - valid course id
     * @return course with a such id
     */
    CourseDto getById(Integer id);

    CourseDto createOrUpdate(CourseDto modifiedCourse);

    void delete(CourseDto dto);

    List<CourseDto> getCourses();
}
