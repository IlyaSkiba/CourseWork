package com.bsu.server.assembler;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.theoretic.test.controller.QuestionController;
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
    @Autowired
    private QuestionController questionController;

    @Override
    public QuestionDto convert(QuestionEntity entity) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(entity.getId());
        questionDto.setQuestion(entity.getQuestion());
        questionDto.setOpenType(entity.getQuestionType());
        questionDto.setTestId(entity.getTest().getId());
        questionDto.setWeight(entity.getWeight());
        //todo: setAnswerList
        return questionDto;
    }

    @Override
    public QuestionEntity convert(QuestionDto entity) {
        QuestionEntity questionEntity = new QuestionEntity();
        if (entity.getId() != null && entity.getId() != 0) {
            questionEntity = questionController.getById(entity.getId());
        }
        questionEntity.setQuestion(entity.getQuestion());
        questionEntity.setQuestionType(entity.getOpenType());
        questionEntity.setTest(testController.getById(entity.getTestId()));
        questionEntity.setWeight(entity.getWeight());
        return questionEntity;
    }
}
