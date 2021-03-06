package com.bsu.server.assembler;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.CourseEntity;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.service.api.dto.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya SKiba
 *         Date: 08.10.12
 */
@Service
public class CourseAssembler extends BaseConverter<CourseDto, CourseEntity> {
    @Autowired
    private ThemeController themeController;
    @Autowired
    private UserController userController;
    @Autowired
    private CourseController courseController;

    @Override
    public CourseDto convert(CourseEntity entity) {
        CourseDto resultDto = new CourseDto();
        resultDto.setCourseName(entity.getCourseName());
        resultDto.setId(entity.getId());
        List<ThemeEntity> themes = entity.getThemes();
        resultDto.setOwnerId(entity.getOwner() == null ? null : entity.getOwner().getId());
        List<Integer> themeIds = new ArrayList<Integer>();
        for (ThemeEntity theme : themes) {
            themeIds.add(theme.getId());
        }
        resultDto.setThemeIds(themeIds);
        return resultDto;
    }

    @Override
    public CourseEntity convert(CourseDto dto) {
        CourseEntity entity = dto.getId() == null ? new CourseEntity() : courseController.getById(dto.getId());
        entity.setId(dto.getId());
        entity.setCourseName(dto.getCourseName());
        entity.setOwner(userController.getById(dto.getOwnerId()));
        List<ThemeEntity> themes = new ArrayList<>();
        if (dto.getThemeIds() != null) {
            for (Integer id : dto.getThemeIds()) {
                themes.add(themeController.getById(id));
            }
        }
        entity.setThemes(themes);
        return entity;
    }
}
