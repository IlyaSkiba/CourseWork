package com.bsu.server.assembler;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.theoretic.test.dto.TestEntity;
import com.bsu.service.api.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author HomeUser
 *         Date: 27.5.13
 *         Time: 23.27
 */
@Component
public class TestAssembler extends BaseConverter<TestDto, TestEntity> {
    @Autowired
    private ThemeController themeController;

    @Override
    public TestDto convert(TestEntity entity) {
        TestDto dto = new TestDto();
        dto.setId(entity.getId());
        dto.setThemeId(entity.getRelatedTheme().getId());
        dto.setQuestionCount(entity.getQuestionCount());
        dto.setWeight(entity.getPointsCount());
        return dto;
    }

    @Override
    public TestEntity convert(TestDto dto) {
        TestEntity entity = new TestEntity();
        entity.setQuestionCount(dto.getQuestionCount());
        entity.setId(dto.getId());
        entity.setPointsCount(dto.getWeight());
        entity.setRelatedTheme(themeController.getById(dto.getThemeId()));
        return entity;
    }
}
