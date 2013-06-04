package com.bsu.server.theoretic.test.service;

import com.bsu.server.assembler.AnswerAssembler;
import com.bsu.server.assembler.QuestionDtoAssembler;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.action.QuestionListAction;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
import com.bsu.server.theoretic.test.dto.TestEntity;
import com.bsu.server.theoretic.test.student.assembler.StudentAnswerAssembler;
import com.bsu.server.theoretic.test.student.controller.StudentAnswerController;
import com.bsu.server.theoretic.test.student.controller.StudentResultController;
import com.bsu.server.theoretic.test.student.entity.StudentAnswerEntity;
import com.bsu.server.theoretic.test.student.entity.StudentResultEntity;
import com.bsu.server.theoretic.themes.controller.StudentStatusController;
import com.bsu.server.theoretic.themes.entity.StudentStatusEntity;
import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.dto.StudentAnswerDto;
import com.bsu.service.api.dto.StudentResultDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
@Transactional
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
    @Autowired
    private QuestionDtoAssembler questionDtoAssembler;
    @Autowired
    private AnswerAssembler answerAssembler;
    @Autowired
    private StudentAnswerAssembler studentAnswerAssembler;
    @Autowired
    private StudentStatusController studentStatusController;

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

        TestEntity testEntity = testController.getById(testId);
        testEntity.getPointsCount();
        return getQuestionIdList(questions, testEntity.getPointsCount(), testEntity.getQuestionCount());
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
        return questionDtoAssembler.convert(questionController.getById(questionId));
    }

    @Override
    public int countResult(List<Integer> questionIds, Integer studentId) {
        double result = 0;
        double maxResult = 0;
        Integer themeId = null;
        for (Integer questionId : questionIds) {
            if (themeId == null) {
                themeId = questionController.getById(questionId).getTest().getRelatedTheme().getId();
            }
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

        return (int) (result / maxResult * 100 * (themeId != null ? getCoefficient(studentId, themeId) : 1));
    }

    private double getCoefficient(Integer studentId, Integer themeId) {
        StudentStatusEntity status = studentStatusController.getByThemeId(themeId, studentId);
        if (status == null) {
            return 1.0;
        } else {
            return Math.max(0.4, Math.pow(0.8, status.getCountOfTries() - 1));
        }
    }

    @Override
    public void saveResults(List<StudentAnswerDto> answerDtos, List<Integer> questionIds, UserDto userDto) {
        UserAccount user = userController.getById(userDto.getId());
        TestEntity testEntity = questionController.getById(questionIds.get(0)).getTest();
        studentAnswerController.cleanupTestResults(user.getId(), testEntity.getId());
        studentResultController.delete(user.getId(), testEntity.getId());
        studentAnswerController.saveResults(Lists.transform(answerDtos, new Function<StudentAnswerDto, StudentAnswerEntity>() {
            @Nullable
            @Override
            public StudentAnswerEntity apply(StudentAnswerDto input) {
                return studentAnswerAssembler.disassemble(input);
            }
        }), user.getId());
        /** Assemble test results      **/
        StudentResultEntity testResult = new StudentResultEntity();
        testResult.setStudent(user);

        testResult.setTestEntity(testEntity);
        testResult.setResult(countResult(questionIds, user.getId()));
        studentStatusController.increaseTryCount(testEntity.getRelatedTheme().getId(), user.getId(), testResult.getResult());
        testResult.setResult(countResult(questionIds, user.getId()));
        studentResultController.saveResult(testResult);
    }

    @Override
    public List<AnswerDto> getAnswers(Integer questionId) {
        ArrayList<AnswerDto> result = new ArrayList<>();
        List<AnswerEntity> dbResult = questionController.getAnswers(questionId);
        for (AnswerEntity entity : dbResult) {
            result.add(answerAssembler.convert(entity));
        }
        return result;
    }

    @Override
    public Integer getTestId(Integer themeId, Integer userId) {
        StudentResultEntity resultDto = studentResultController.
                getStudentResult(userId, testController.getTestFromTheme(themeId).getId());
        if (resultDto != null && resultDto.getResult() >= 40) {
            return null;
        }
        return testController.getTestFromTheme(themeId).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentResultDto> getStudentResults(Integer userId) {
        UserAccount user = userController.getById(userId);
        ArrayList<StudentResultDto> result = new ArrayList<>();
        List<StudentResultEntity> dbResult = studentResultController.getStudentResults(user.getId());
        for (StudentResultEntity entity : dbResult) {
            StudentResultDto answerDto = new StudentResultDto();
            answerDto.setTestName(entity.getTestEntity().getRelatedTheme().getName());
            answerDto.setResultScore(entity.getResult());
            answerDto.setUserName(user.getUsername());
            result.add(answerDto);
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentResultDto> getThemeResults(Integer themeId) {
        ArrayList<StudentResultDto> result = new ArrayList<>();
        TestEntity test = testController.getTestFromTheme(themeId);
        if (test == null) {
            return Collections.emptyList();
        }
        List<StudentResultEntity> dbResult = studentResultController.getResults(test.getId());
        for (StudentResultEntity entity : dbResult) {
            StudentResultDto answerDto = new StudentResultDto();
            answerDto.setTestName(entity.getTestEntity().getRelatedTheme().getName());
            answerDto.setResultScore(entity.getResult());
            answerDto.setUserName(entity.getStudent().getUsername());
            result.add(answerDto);
        }
        return result;
    }

    @Override
    public void addQuestion(QuestionDto questionEntity) {
        //@TODO: implement
    }


}
