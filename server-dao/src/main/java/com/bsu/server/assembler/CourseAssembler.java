package com.bsu.server.assembler;

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
public class CourseAssembler {
    @Autowired
    private ThemeController themeController;
    @Autowired
    private UserController userController;
    @Autowired
    private CourseController courseController;

    public CourseDto assemble(CourseEntity entity) {
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

    public CourseEntity dissassemble(CourseDto dto) {
        CourseEntity entity = dto.getId() == null ? new CourseEntity() : courseController.getEntity(dto.getId());
        entity.setId(dto.getId());
        entity.setCourseName(dto.getCourseName());
        entity.setOwner(userController.getUser(dto.getOwnerId()));
        List<ThemeEntity> themes = new ArrayList<>();
        if (dto.getThemeIds() != null) {
            for (Integer id : dto.getThemeIds()) {
                themes.add(themeController.getTheme(id));
            }
        }
        entity.setThemes(themes);
        return entity;
    }
}