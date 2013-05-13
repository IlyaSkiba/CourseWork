package com.bsu.service.api.theoretic;

import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.dto.UserDto;

import java.util.List;

/**
 * @author Ilya Skiba
 */
public interface ThemeService extends SearchableService<ThemeDto> {
    List<ThemeDto> getThemesForCourse(CourseDto selectedCourse);

    List<ThemeDto> getThemesForCourse(Integer selectedCourseId, UserDto user);
}
