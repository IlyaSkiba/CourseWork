package com.bsu.server.theoretic.test.service;

import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.action.QuestionListAction;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.AnswerDto;
import com.bsu.server.theoretic.test.dto.QuestionDto;
import com.bsu.server.theoretic.test.dto.TestDto;
import com.bsu.server.theoretic.test.student.controller.StudentAnswerController;
import com.bsu.server.theoretic.test.student.controller.StudentResultController;
import com.bsu.server.theoretic.test.student.dto.StudentAnswerDto;
import com.bsu.server.theoretic.test.student.dto.StudentResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    @Autowired
    private StudentResultController studentResultController;

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
            if (answerDtos.size() == rightAnswers.size()) {
                correctives = 1;
                Collections.sort(rightAnswers, new Comparator<AnswerDto>() {
                    @Override
                    public int compare(AnswerDto o1, AnswerDto o2) {
                        return o1.getTextAnswer().compareTo(o2.getTextAnswer());
                    }
                });
                Collections.sort(answerDtos, new Comparator<StudentAnswerDto>() {
                    @Override
                    public int compare(StudentAnswerDto o1, StudentAnswerDto o2) {
                        return o1.getAnswerText().compareTo(o2.getAnswerText());
                    }
                });
                for (int i = 0; i < answerDtos.size(); i++) {
                    if (!answerDtos.get(i).getAnswerText().equalsIgnoreCase(rightAnswers.get(i).getTextAnswer())) {
                        correctives = 0;
                        break;
                    }
                }
            }
            result += correctives * questionController.getQuestionDto(questionId).getWeight();
            maxResult += questionController.getQuestionDto(questionId).getWeight();
        }
        return (int) (result / maxResult * 100);
    }

    public void saveResults(List<StudentAnswerDto> answerDtos, List<Integer> questionIds) {
        studentAnswerController.cleanupTestResults(answerDtos.get(0).getStudent().getId(),
                answerDtos.get(0).getQuestion().getTest().getId());
        studentAnswerController.saveResults(answerDtos, answerDtos.get(0).getStudent().getId());
        /** Assemble test results*/
        StudentResultDto testResult = new StudentResultDto();
        testResult.setStudent(answerDtos.get(0).getStudent());
        testResult.setTestDto(answerDtos.get(0).getQuestion().getTest());
        testResult.setResult(countResult(questionIds, answerDtos.get(0).getStudent().getId()));
        studentResultController.saveResult(testResult);
    }

    public List<AnswerDto> getAnswers(Integer questionId) {
        return questionController.getAnswers(questionId);
    }

    public Integer getTestId(Integer themeId, Integer userId) {
        try {
            StudentResultDto resultDto = studentResultController.
                    getStudentResult(userId, testController.getTestFromTheme(themeId).getId());
            if (resultDto != null) return null;
        } catch (Exception e) {
        }

        return testController.getTestFromTheme(themeId).getId();
    }

    public List<StudentResultDto> getStudentResults(UserAccount user) {
        return studentResultController.getStudentResults(user.getId());
    }

    public void addQuestion(QuestionDto questionDto) {
        //@TODO: implement
    }
}
