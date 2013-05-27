package com.bsu.server.theoretic.test.service;

import com.bsu.server.assembler.TestAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.TestEntity;
import com.bsu.service.api.dto.TestDto;
import com.bsu.service.api.theoretic.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HomeUser
 *         Date: 27.5.13
 *         Time: 23.09
 */
@Service
public class TestServiceImpl extends BaseSearchableServiceImplImpl<TestDto, TestEntity> implements TestService {
    @Autowired
    private TestController testController;
    @Autowired
    private TestAssembler testAssembler;

    @Override
    protected BaseController<TestEntity> getController() {
        return testController;
    }

    @Override
    protected BaseConverter<TestDto, TestEntity> getConverter() {
        return testAssembler;
    }

    @Override
    public TestDto getByThemeId(Integer themeId) {
        TestEntity test = testController.getTestFromTheme(themeId);
        if (test == null) {
            return null;
        }
        return getConverter().convert(test);
    }
}
