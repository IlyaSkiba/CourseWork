package com.bsu.server.theoretic.test.service;

import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
import junit.framework.Assert;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 05.09.12
 * Time: 14:47
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/controller-beans.xml"})
public class TestDocDecoder {
    @Autowired
    private DocToDatabaseDecoder decoder;
    @Autowired
    private TheoreticTestService testService;


    @Test
    public void testDecoding() throws Exception {
        final QuestionDto resultDto = new QuestionDto();
        resultDto.setQuestion("Test Doc creation");
        resultDto.setOpenType(false);
        resultDto.setAnswerDtos(Arrays.asList(createAnswer(1, "Answ1", false), createAnswer(2, "Answ2", true),
                createAnswer(3, "Answ3", false), createAnswer(4, "Answ4", true)));

        FileInputStream inputStream = new FileInputStream("src/test/resources/InputSource.docx");
        testService.addQuestion(EasyMock.capture(new Capture<QuestionDto>() {
            @Override
            public void setValue(QuestionDto to) {
                Assert.assertEquals(to.getQuestion(), resultDto.getQuestion());
                Assert.assertEquals(to.getAnswerDtos().size(), resultDto.getAnswerDtos().size());
                for (int i = 0; i < to.getAnswerDtos().size(); i++) {
                    Assert.assertEquals(to.getAnswerDtos().get(i).getId(), resultDto.getAnswerDtos().get(i).getId());
                    Assert.assertEquals(to.getAnswerDtos().get(i).getAnswer(),
                            resultDto.getAnswerDtos().get(i).getAnswer());
                    Assert.assertEquals(to.getAnswerDtos().get(i).isRight(),
                            resultDto.getAnswerDtos().get(i).isRight());
                }
            }
        }));
        EasyMock.expectLastCall();

        EasyMock.replay(testService);

        decoder.decode(inputStream);
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
