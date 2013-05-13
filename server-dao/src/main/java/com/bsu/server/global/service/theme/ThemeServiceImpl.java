package com.bsu.server.global.service.theme;

import com.bsu.server.assembler.ThemeDtoAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.theoretic.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class ThemeServiceImpl extends BaseSearchableServiceImplImpl<ThemeDto, ThemeEntity> implements ThemeService {
    @Autowired
    private ThemeController themeController;
    @Autowired
    private ThemeDtoAssembler themeDtoAssembler;

    @Override
    public List<ThemeDto> getThemesForCourse(CourseDto selectedCourse) {
        return convertList(themeController.getAllThemesInCourse(selectedCourse.getId()));
    }

    @Override
    public List<ThemeDto> getThemesForCourse(Integer selectedCourseId, UserDto user) {
        return convertList(themeController.getThemesForCourse(selectedCourseId, user.getId()));
    }

    @Override
    protected BaseController<ThemeEntity> getController() {
        return themeController;
    }

    @Override
    protected BaseConverter<ThemeDto, ThemeEntity> getConverter() {
        return themeDtoAssembler;
    }
}
