package com.bsu.server.theoretic.test.service;

import com.bsu.server.theoretic.test.QuestionListAction;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.QuestionDto;
import com.bsu.server.theoretic.test.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class TheoreticTestService {

    @Autowired
    private QuestionController questionController;

    @Autowired
    private TestController testController;

    public List<Integer> getQuestionIds(Integer themeId) {
        Integer testId = testController.getTestFromTheme(themeId).getId();
        List<QuestionDto> questions = questionController.getQuestionsForTest(testId);
        if (questions == null || questions.isEmpty()) {
            return Collections.emptyList();
        }

        TestDto testDto = testController.getTest(testId);
        testDto.getPointsCount();
        return getQuestionIdList(questions, testDto.getPointsCount(), testDto.getQuestionCount());
    }

    private List<Integer> getQuestionIdList(List<QuestionDto> questionDtos,
                                            Integer pointsCount, Integer questionsCount) {
        List<QuestionDto> shuffledList;
        if (questionsCount == null) {
            if (pointsCount == null) {
                return Collections.emptyList();
            }

            shuffledList = new QuestionListAction().getIdsByPoints(questionDtos, pointsCount);

        } else {
            shuffledList = new QuestionListAction().shuffle(questionDtos);
            if (questionsCount < questionDtos.size()) {

                shuffledList = shuffledList.subList(0, questionsCount);
            }
        }

        List<Integer> questionIds = new ArrayList<Integer>(shuffledList.size());
        for (QuestionDto question : shuffledList) {
            questionIds.add(question.getId());
        }
        return questionIds;

    }

    public QuestionDto getQuestion(Integer questionId) {
        return questionController.getQuestionDto(questionId);
    }
}
