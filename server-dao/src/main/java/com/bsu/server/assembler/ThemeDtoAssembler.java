package com.bsu.server.assembler;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.service.api.dto.ThemeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 30.08.12
 * Time: 22:27
 */
@Component
public class ThemeDtoAssembler extends BaseConverter<ThemeDto, ThemeEntity> {
    @Autowired
    private UserController userController;
    @Autowired
    private CourseController courseController;

    @Override
    public ThemeDto convert(ThemeEntity entity) {
        ThemeDto dto = new ThemeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatorName(entity.getCreator().getUsername());
        dto.setCourseId(entity.getCourseEntity().getId());
        return dto;
    }

    @Override
    public ThemeEntity convert(ThemeDto entity) {
        ThemeEntity newEntity = new ThemeEntity();
        newEntity.setId(entity.getId());
        newEntity.setName(entity.getName());
        newEntity.setCreator(userController.getUserByUsername(entity.getCreatorName()));
        newEntity.setCourseEntity(courseController.getById(entity.getCourseId()));
        return newEntity;
    }
}
