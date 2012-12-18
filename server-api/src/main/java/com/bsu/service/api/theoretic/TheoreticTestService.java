package com.bsu.service.api.theoretic;

import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.dto.StudentAnswerDto;
import com.bsu.service.api.dto.StudentResultDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 02.09.12
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public interface TheoreticTestService {
    List<Integer> getQuestionIds(Integer themeId);

    QuestionDto getQuestion(Integer questionId);

    public int countResult(List<Integer> questionIds, Integer studentId);

    public void saveResults(List<StudentAnswerDto> answerDtos, List<Integer> questionIds);

    public List<AnswerDto> getAnswers(Integer questionId);

    public Integer getTestId(Integer themeId, Integer userId);

    public List<StudentResultDto> getStudentResults(Integer userId);

    public void addQuestion(QuestionDto questionDto);
}
