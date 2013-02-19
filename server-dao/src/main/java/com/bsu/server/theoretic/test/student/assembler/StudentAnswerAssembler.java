package com.bsu.server.theoretic.test.student.assembler;

import com.bsu.server.controller.UserController;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.student.entity.StudentAnswerEntity;
import com.bsu.service.api.dto.StudentAnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 08.10.12
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentAnswerAssembler {
    @Autowired
    private QuestionController questionController;
    @Autowired
    private UserController userController;

    public StudentAnswerDto assemble(StudentAnswerEntity entity) {
        StudentAnswerDto resultDto = new StudentAnswerDto();
        resultDto.setAnswer(entity.getAnswerText());
        resultDto.setQuestionId(entity.getQuestion().getId());
        resultDto.setUserId(entity.getStudent().getId());

        return resultDto;
    }

    public StudentAnswerEntity disassemble(StudentAnswerDto dto) {
        StudentAnswerEntity answerEntity = new StudentAnswerEntity();
        answerEntity.setAnswerText(dto.getAnswer());
        answerEntity.setQuestion(questionController.getQuestionDto(dto.getQuestionId()));
        answerEntity.setStudent(userController.getById(dto.getUserId()));

        return answerEntity;
    }
}
