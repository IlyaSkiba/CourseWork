package com.bsu.server.global.service.theme;

import com.bsu.server.assembler.ThemeDtoAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.server.theoretic.themes.controller.StudentStatusController;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.theoretic.ThemeService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
@Transactional(readOnly = true)
public class ThemeServiceImpl extends BaseSearchableServiceImplImpl<ThemeDto, ThemeEntity> implements ThemeService {
    @Autowired
    private ThemeController themeController;
    @Autowired
    private ThemeDtoAssembler themeDtoAssembler;
    @Autowired
    private StudentStatusController studentStatusController;

    @Override
    @Transactional
    public List<ThemeDto> getThemesForCourse(CourseDto selectedCourse) {
        return convertList(themeController.getAllThemesInCourse(selectedCourse.getId()));
    }

    @Override
    public List<ThemeDto> getThemesForCourse(Integer selectedCourseId, UserDto user, int significance) {
        return convertList(studentStatusController.filterThemesByAccessibility(themeController.getThemesForCourse(selectedCourseId,
                user.getId()), ThemeEntity.Significance.parse(significance)));
    }

    @Override
    @Transactional(readOnly = false)
    public void updateThemeParents(ThemeDto theme, List<ThemeDto> selectedDtos) {
        Validate.notNull(theme.getId());
        theme.setParentThemes(Lists.transform(selectedDtos, new Function<ThemeDto, Integer>() {
            @Override
            public Integer apply(ThemeDto input) {
                return input.getId();
            }
        }));
        validateUpdating(theme, theme.getId());
        List<ThemeEntity> parents = Lists.transform(theme.getParentThemes(), new Function<Integer, ThemeEntity>() {
            @Override
            public ThemeEntity apply(Integer id) {
                return getController().getById(id);
            }
        });
        ThemeEntity entityToSave = getConverter().convert(theme);
        entityToSave.getParentThemes().clear();
        entityToSave.getParentThemes().addAll(parents);
        getConverter().convert(getController().update(entityToSave));
    }

    private void validateUpdating(ThemeDto theme, Integer markedTheme) {
        for (Integer themeId : theme.getParentThemes()) {
            if (markedTheme.equals(themeId)) {
                throw new RuntimeException("OLOLO"); //TODO: Create CycleThemeException
            }
            validateUpdating(getById(themeId), markedTheme);
        }
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
