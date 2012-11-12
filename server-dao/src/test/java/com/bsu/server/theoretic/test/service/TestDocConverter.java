package com.bsu.server.theoretic.test.service;

import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 02.09.12
 * Time: 16:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/controller-beans.xml"})
public class TestDocConverter {
    @Autowired
    private DatabaseBaseToDocConverter converter;
    @Autowired
    private TheoreticTestService testService;

    @Test
    public void testGenerateReport() throws Exception {
        QuestionDto resultDto = new QuestionDto();
        resultDto.setQuestion("Test Doc creation");
        resultDto.setOpenType(0);
        resultDto.setAnswerDtos(Arrays.asList(createAnswer(1, "Answ1", false), createAnswer(2, "Answ2", true),
                createAnswer(3, "Answ3", false), createAnswer(4, "Answ4", true)));

        EasyMock.expect(testService.getQuestion(101)).andReturn(resultDto);

        EasyMock.expect(testService.getQuestionIds(1)).andReturn(Arrays.asList(101));
        EasyMock.replay(testService);

        converter.generateReport(1);
        EasyMock.verify(testService);
    }

    private AnswerDto createAnswer(Integer id, String text, boolean isRight) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(id);
        answerDto.setAnswer(text);
        answerDto.setRight(isRight);
        return answerDto;
    }
}
