package com.bsu.server.theoretic.test.service;

import com.bsu.server.assembler.QuestionAssembler;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.action.QuestionListAction;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
import com.bsu.server.theoretic.test.dto.TestDto;
import com.bsu.server.theoretic.test.student.controller.StudentAnswerController;
import com.bsu.server.theoretic.test.student.controller.StudentResultController;
import com.bsu.server.theoretic.test.student.dto.StudentAnswerEntity;
import com.bsu.server.theoretic.test.student.dto.StudentResultEntity;
import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.dto.StudentAnswerDto;
import com.bsu.service.api.dto.StudentResultDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
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
public class TheoreticTestServiceImpl implements TheoreticTestService {

    @Autowired
    private QuestionController questionController;

    @Autowired
    private TestController testController;

    @Autowired
    private StudentAnswerController studentAnswerController;
    @Autowired
    private StudentResultController studentResultController;
    @Autowired
    private UserController userController;

    @Override
    public List<Integer> getQuestionIds(Integer themeId) {
        Integer testId;
        try {
            testId = testController.getTestFromTheme(themeId).getId();
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
        List<QuestionEntity> questions = questionController.getQuestionsForTest(testId);
        if (questions == null || questions.isEmpty()) {
            return Collections.emptyList();
        }

        TestDto testDto = testController.getTest(testId);
        testDto.getPointsCount();
        return getQuestionIdList(questions, testDto.getPointsCount(), testDto.getQuestionCount());
    }


    private List<Integer> getQuestionIdList(List<QuestionEntity> questionEntities,
                                            Integer pointsCount, Integer questionsCount) {
        List<QuestionEntity> shuffledList;
        if (questionsCount == null) {
            if (pointsCount == null) {
                return Collections.emptyList();
            }

            shuffledList = new QuestionListAction().getIdsByPoints(questionEntities, pointsCount);

        } else {
            shuffledList = new QuestionListAction().shuffle(questionEntities);
            if (questionsCount < questionEntities.size()) {

                shuffledList = shuffledList.subList(0, questionsCount);
            }
        }

        List<Integer> questionIds = new ArrayList<Integer>(shuffledList.size());
        for (QuestionEntity question : shuffledList) {
            questionIds.add(question.getId());
        }
        return questionIds;

    }

    @Override
    public QuestionDto getQuestion(Integer questionId) {
        return QuestionAssembler.assemble(questionController.getQuestionDto(questionId));
    }

    @Override
    public int countResult(List<Integer> questionIds, Integer studentId) {
        double result = 0;
        double maxResult = 0;
        for (Integer questionId : questionIds) {
            List<AnswerEntity> rightAnswers = questionController.getRightAnswers(questionId);
            double correctives = 0;
            List<StudentAnswerEntity> answerDtos = studentAnswerController.getAnswers(questionId, studentId);
            if (answerDtos.size() == rightAnswers.size()) {
                correctives = 1;
                Collections.sort(rightAnswers, new Comparator<AnswerEntity>() {
                    @Override
                    public int compare(AnswerEntity o1, AnswerEntity o2) {
                        return o1.getTextAnswer().compareTo(o2.getTextAnswer());
                    }
                });
                Collections.sort(answerDtos, new Comparator<StudentAnswerEntity>() {
                    @Override
                    public int compare(StudentAnswerEntity o1, StudentAnswerEntity o2) {
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

    @Override
    public void saveResults(List<StudentAnswerDto> answerDtos, List<Integer> questionIds) {
        //TODO: refactor id
       /* studentAnswerController.cleanupTestResults(answerDtos.get(0).getStudent().getId(),
                answerDtos.get(0).getQuestion().getTest().getId());
        studentAnswerController.saveResults(answerDtos, answerDtos.get(0).getStudent().getId());
        /** Assemble test results
        StudentResultEntity testResult = new StudentResultEntity();
        testResult.setStudent(answerDtos.get(0).getStudent());
        testResult.setTestDto(answerDtos.get(0).getQuestion().getTest());
        testResult.setResult(countResult(questionIds, answerDtos.get(0).getStudent().getId()));
        studentResultController.saveResult(testResult);                                        */
    }

    @Override
    public List<AnswerDto> getAnswers(Integer questionId) {
        ArrayList<AnswerDto> result = new ArrayList<AnswerDto>();
        List<AnswerEntity> dbResult = questionController.getAnswers(questionId);
        for (AnswerEntity entity : dbResult) {
            AnswerDto answerDto = new AnswerDto();
            result.add(answerDto);
        }
        return result;
    }

    @Override
    public Integer getTestId(Integer themeId, Integer userId) {
        try {
            StudentResultEntity resultDto = studentResultController.
                    getStudentResult(userId, testController.getTestFromTheme(themeId).getId());
            if (resultDto != null) {
                return null;
            }
        } catch (Exception e) {
        }

        return testController.getTestFromTheme(themeId).getId();
    }

    @Override
    public List<StudentResultDto> getStudentResults(Integer userId) {
        UserAccount user = userController.getUser(userId);
        ArrayList<StudentResultDto> result = new ArrayList<StudentResultDto>();
        List<StudentResultEntity> dbResult = studentResultController.getStudentResults(user.getId());
        for (StudentResultEntity entity : dbResult) {
            StudentResultDto answerDto = new StudentResultDto();
            result.add(answerDto);
        }
        return result;
    }

    @Override
    public void addQuestion(QuestionDto questionEntity) {
        //@TODO: implement
    }


}
