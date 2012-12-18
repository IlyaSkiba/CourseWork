package com.bsu.service.api.global.admin;

import com.bsu.service.api.dto.CourseDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 08.10.12
 * Time: 12:05
 */
public interface CourseService {
    /**
     * @param id - valid course id
     * @return course with a such id
     */
    CourseDto getCourse(Integer id);

    void updateCourse(CourseDto modifiedCourse);

    void deleteCourse(Integer id);

    CourseDto createCourse(CourseDto newCourse);

    List<CourseDto> getCourses();
}
