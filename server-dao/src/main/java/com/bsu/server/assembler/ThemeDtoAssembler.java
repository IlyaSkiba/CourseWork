package com.bsu.server.assembler;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.service.api.dto.ThemeDto;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 30.08.12
 * Time: 22:27
 */
@Component
@Transactional(readOnly = true)
public class ThemeDtoAssembler extends BaseConverter<ThemeDto, ThemeEntity> {
    @Autowired
    private UserController userController;
    @Autowired
    private CourseController courseController;
    @Autowired
    private ThemeController themeController;

    @Override
    public ThemeDto convert(ThemeEntity entity) {
        ThemeDto dto = new ThemeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatorName(entity.getCreator().getUsername());
        dto.setCourseId(entity.getCourseEntity().getId());
        List<Integer> parentIds = Lists.newArrayList();
        if (entity.getParentThemes() != null) {
            for (ThemeEntity parent : entity.getParentThemes()) {
                parentIds.add(parent.getId());
            }

        }
        parentIds.size();
        dto.setParentThemes(parentIds);
        dto.getParentThemes().size();
        return dto;
    }

    @Override
    public ThemeEntity convert(ThemeDto entity) {
        ThemeEntity newEntity = new ThemeEntity();
        if (entity.getId() != null) {
            newEntity = themeController.getById(entity.getId());
        } else {
            newEntity.setCreator(userController.getUserByUsername(entity.getCreatorName()));
            newEntity.setCourseEntity(courseController.getById(entity.getCourseId()));
        }
        newEntity.setName(entity.getName());
        return newEntity;
    }
}
