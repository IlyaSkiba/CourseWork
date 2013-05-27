package com.bsu.server.assembler;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
import com.bsu.service.api.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author HomeUser
 *         Date: 14.5.13
 *         Time: 20.53
 */
@Component
public class QuestionDtoAssembler extends BaseConverter<QuestionDto, QuestionEntity> {
    @Autowired
    private TestController testController;

    @Override
    public QuestionDto convert(QuestionEntity entity) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(entity.getId());
        questionDto.setQuestion(entity.getQuestion());
        questionDto.setOpenType(entity.getQuestionType());
        questionDto.setTestId(entity.getTest().getId());
        //todo: setAnswerList
        return questionDto;
    }

    @Override
    public QuestionEntity convert(QuestionDto entity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(entity.getId());
        questionEntity.setQuestion(entity.getQuestion());
        questionEntity.setQuestionType(entity.getOpenType());
        questionEntity.setTest(testController.getById(entity.getTestId()));
        //todo setAnswerList
        return questionEntity;
    }
}
