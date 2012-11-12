package com.bsu.server.theoretic.test.service;

import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 03.09.12
 * Time: 23:24
 */
@Service
public class DocToDatabaseDecoder {
    private static final List<String> ANSWER_PREFIXES = Arrays.asList("Идентификатор: ", "Текст: ",
            "Ответ правильный: ");
    @Autowired
    private TheoreticTestService testService;

    public void decode(InputStream is) throws IOException {

        XWPFDocument document = new XWPFDocument(is);
        List<XWPFParagraph> paragraphList = document.getParagraphs();
        List<QuestionDto> questions = new ArrayList<QuestionDto>();
        int paragraphIndex = 0;
        while (paragraphIndex < paragraphList.size()) {
            QuestionDto questionDto = new QuestionDto();
            paragraphIndex = getQuestion(paragraphIndex, paragraphList, questionDto);
            questions.add(questionDto);
            testService.addQuestion(questionDto);
        }
    }

    private Integer getQuestion(Integer position, List<XWPFParagraph> paragraphList, QuestionDto questionDto)
            throws IOException {
        Integer tempPosition = position;
        XWPFParagraph paragraph = paragraphList.get(tempPosition++);
        List<XWPFRun> paragraphRuns = paragraph.getRuns();
        validateRuns(paragraphRuns);
        try {
            String qId = paragraphRuns.get(1).getText(0);
            questionDto.setId(Integer.parseInt(qId.trim()));
        } catch (NumberFormatException e) {
            questionDto.setId(null);
        }

        paragraph = paragraphList.get(tempPosition++);
        paragraphRuns = paragraph.getRuns();
        validateRuns(paragraphRuns);
        StringBuilder questBuilder = new StringBuilder();
        for (int i = 1; i < paragraphRuns.size(); i++) {
            questBuilder.append(paragraphRuns.get(i).getText(0));
        }
        questionDto.setQuestion(questBuilder.toString().trim());

        paragraph = paragraphList.get(tempPosition++);
        paragraphRuns = paragraph.getRuns();
        validateRuns(paragraphRuns);
        Boolean hasAnswers = Boolean.parseBoolean(paragraphRuns.get(1).getText(0).trim());
        questionDto.setOpenType(hasAnswers ? 1 : 0);

        if (!hasAnswers) {
            return tempPosition;
        }

        paragraph = paragraphList.get(tempPosition++);
        questionDto.setAnswerDtos(new ArrayList<AnswerDto>());
        while (!paragraph.isPageBreak()) {
            paragraphRuns = paragraph.getRuns();
            validateRuns(paragraphRuns);
            questionDto.getAnswerDtos().add(getAnswer(paragraph));
            paragraph = paragraphList.get(tempPosition++);
        }

        return tempPosition;
    }

    private AnswerDto getAnswer(XWPFParagraph paragraph) {
        AnswerDto answerDto = new AnswerDto();
        List<XWPFRun> runs = paragraph.getRuns();
        String id = runs.get(1).getText(0);
        id = id.replace(ANSWER_PREFIXES.get(0), StringUtils.EMPTY);
        try {
            answerDto.setId(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            answerDto.setId(null);
        }

        String name = runs.get(2).getText(0);
        name = name.replace(ANSWER_PREFIXES.get(1), StringUtils.EMPTY);
        answerDto.setAnswer(name);

        String isRight = runs.get(3).getText(0);
        isRight = isRight.replace(ANSWER_PREFIXES.get(2), StringUtils.EMPTY);
        try {
            answerDto.setRight(Boolean.parseBoolean(isRight));
        } catch (NumberFormatException e) {
            answerDto.setRight(false);
        }
        return answerDto;
    }

    private void validateRuns(List<XWPFRun> runs) throws IOException {
        if (runs.size() < 2) {
            throw new IOException("template file is wrong");
        }
    }
}
