package com.bsu.server.theoretic.test.service;

import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class TheoreticTestService {

    @Autowired
    private QuestionController questionController;

    public List<Integer> getQuestionIds(Integer testId) {
        return questionController.getQuestionIds(testId);
    }

    public QuestionDto getQuestion(Integer questionId) {
        return questionController.getQuestionDto(questionId);
    }
}
