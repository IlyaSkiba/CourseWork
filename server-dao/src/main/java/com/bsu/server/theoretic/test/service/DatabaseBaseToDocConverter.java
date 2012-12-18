package com.bsu.server.theoretic.test.service;

import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 02.09.12
 * Time: 15:25
 */
@Service
public class DatabaseBaseToDocConverter {
    @Autowired
    private TheoreticTestService testService;

    public void generateReport(Integer themeId) throws IOException {
        List<Integer> questionIds = testService.getQuestionIds(themeId);

        String fileName = String.format("TempFile%f.docx", Math.random());
        XWPFDocument document = new XWPFDocument();

        for (Integer questionId : questionIds) {
            putQuestion(questionId, document);
        }
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            document.write(outStream);
            if (outStream != null) {
                outStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void putQuestion(Integer quiestionId, XWPFDocument document) {
        QuestionDto question = testService.getQuestion(quiestionId);

        XWPFParagraph idQuestHeader = document.createParagraph();
        addTextHeader("Идентификатор:", idQuestHeader);

        XWPFRun idQuestText = idQuestHeader.createRun();
        idQuestText.setText(question.getId() + "");
        idQuestText.addCarriageReturn();
        idQuestText.addCarriageReturn();
        idQuestHeader.addRun(idQuestText);

        XWPFParagraph questHeader = document.createParagraph();
        addTextHeader("Вопрос:", questHeader);

        XWPFRun questText = questHeader.createRun();
        questText.setText(question.getQuestion());
        questText.addCarriageReturn();
        questText.addCarriageReturn();
        questHeader.addRun(questText);

        XWPFParagraph hasAnswersParagraph = document.createParagraph();
        addTextHeader("Есть варианты ответов:", hasAnswersParagraph);

        XWPFRun hasAnswers = hasAnswersParagraph.createRun();
        hasAnswers.setText((question.getOpenType() > 0) + "");
        hasAnswers.setBold(true);
        hasAnswers.addCarriageReturn();
        hasAnswers.addCarriageReturn();
        hasAnswersParagraph.addRun(hasAnswers);

        if (question.getOpenType() > 0) {
            hasAnswersParagraph.setPageBreak(true);
            return;
        }

        for (AnswerDto answerDto : question.getAnswerDtos()) {
            XWPFParagraph answerParagraph = document.createParagraph();
            addTextHeader("Ответ:", answerParagraph);

            XWPFRun idRun = answerParagraph.createRun();
            idRun.setText(String.format("Идентификатор: %d", answerDto.getId()));
            idRun.addCarriageReturn();

            XWPFRun textRun = answerParagraph.createRun();
            textRun.setText(String.format("Текст: %s", answerDto.getAnswer()));
            textRun.addCarriageReturn();

            XWPFRun isRight = answerParagraph.createRun();
            isRight.setText(String.format("Ответ правильный: %s", answerDto.isRight()));
            isRight.addCarriageReturn();
            isRight.addCarriageReturn();
        }
        XWPFParagraph emptyParagraph = document.createParagraph();
        emptyParagraph.setPageBreak(true);
    }

    private void addTextHeader(String text, XWPFParagraph paragraph) {
        XWPFRun headerRun = paragraph.createRun();
        headerRun.setText(text);
        headerRun.setBold(true);
        headerRun.setItalic(true);
        headerRun.addCarriageReturn();
        headerRun.addCarriageReturn();
        paragraph.addRun(headerRun);
    }
}
