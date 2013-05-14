package com.bsu.server.global.service.question;

import com.bsu.server.assembler.QuestionDtoAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.theoretic.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HomeUser
 *         Date: 14.5.13
 *         Time: 20.44
 */
@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl extends BaseSearchableServiceImplImpl<QuestionDto,QuestionEntity> implements QuestionService {
    @Autowired
    private QuestionController questionController;
    @Autowired
    private QuestionDtoAssembler questionDtoAssembler;

    @Override
    protected BaseController<QuestionEntity> getController() {
        return questionController;
    }

    @Override
    protected BaseConverter<QuestionDto, QuestionEntity> getConverter() {
        return questionDtoAssembler;
    }
}
