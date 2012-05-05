package com.bsu.server.theoretic.test.service;

import com.bsu.server.theoretic.test.action.QuestionListAction;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.AnswerDto;
import com.bsu.server.theoretic.test.dto.QuestionDto;
import com.bsu.server.theoretic.test.dto.TestDto;
import com.bsu.server.theoretic.test.student.controller.StudentAnswerController;
import com.bsu.server.theoretic.test.student.dto.StudentAnswerDto;
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

    @Autowired
    private StudentAnswerController studentAnswerController;

    public List<Integer> getQuestionIds(Integer themeId) {
        Integer testId;
        try {
            testId = testController.getTestFromTheme(themeId).getId();
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
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

    public int countResult(List<Integer> questionIds, Integer studentId) {
        double result = 0;
        double maxResult = 0;
        for (Integer questionId : questionIds) {
            List<AnswerDto> rightAnswers = questionController.getRightAnswers(questionId);
            double correctives = 0;
            List<StudentAnswerDto> answerDtos = studentAnswerController.getAnswers(questionId, studentId);
            for (StudentAnswerDto studentAnswerDto : answerDtos) {
                for (AnswerDto rightAnswer : rightAnswers) {
                    if (rightAnswer.getTextAnswer().trim().equalsIgnoreCase(studentAnswerDto.getAnswerText().trim())) {
                        correctives += 1;
                    }
                }
            }
            correctives = correctives * 2 - answerDtos.size();
            if (correctives <= 0) continue;
            result += correctives * questionController.getQuestionDto(questionId).getWeight() / rightAnswers.size();
            maxResult += questionController.getQuestionDto(questionId).getWeight();
        }
        return (int) (result / maxResult * 100);
    }

    public void saveResults(List<StudentAnswerDto> answerDtos) {
        studentAnswerController.cleanupTestResults(answerDtos.get(0).getStudent().getId(),
                answerDtos.get(0).getQuestion().getTest().getId());
        studentAnswerController.saveResults(answerDtos, answerDtos.get(0).getStudent().getId());

    }

    public List<AnswerDto> getAnswers(Integer questionId) {
        return questionController.getAnswers(questionId);
    }

    public Integer getTestId(Integer themeId) {
        return testController.getTestFromTheme(themeId).getId();
    }
}
