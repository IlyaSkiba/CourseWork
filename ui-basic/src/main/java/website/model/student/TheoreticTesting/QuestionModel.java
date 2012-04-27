package website.model.student.TheoreticTesting;

import com.bsu.server.theoretic.test.dto.AnswerDto;
import com.bsu.server.theoretic.test.dto.QuestionDto;
import com.bsu.server.theoretic.test.service.TheoreticTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: HomeUser
 * Date: 6.2.12
 * Time: 15.36
 */
@Scope("session")
@Named()
public class QuestionModel {
    @Autowired
    private TheoreticTestingModel model;
    @Autowired
    private TheoreticTestService testService;
    private String question;
    private boolean answerType = false;
    private String answer;
    private List<String> selectedCheck;
    private List<String> allCheck;

    public int getQuestionNumber() {
        return questionNumber;
    }

    private int questionNumber = 0;

    public void initializeQuestion(Integer questionId) {
        QuestionDto question = testService.getQuestion(model.getIdQuestionList().get(questionId));
        setQuestion(question.getQuestion());
        setAnswerType(question.getQuestionType() > 0);
        List<AnswerDto> answerDtoList = testService.getAnswers(model.getIdQuestionList().get(questionId));
        if (!isAnswerType()) {
            List<String> checks = new ArrayList<String>();
            for (AnswerDto answer : answerDtoList) {
                checks.add(answer.getTextAnswer());
            }
            setAllCheck(checks);
        }
    }

    public List<String> getSelectedCheck() {
        return selectedCheck;
    }

    public void setSelectedCheck(List<String> selectedCheck) {
        this.selectedCheck = selectedCheck;
    }

    public List<String> getAllCheck() {
        return allCheck;
    }

    public void setAllCheck(List<String> allCheck) {
        this.allCheck = allCheck;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        if (question == null || question.isEmpty()) {
            initializeQuestion(questionNumber);
        }
        return question;
    }

    public boolean isAnswerType() {
        return answerType;
    }

    public void setAnswerType(boolean answerType) {
        this.answerType = answerType;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private StudentAnswer saveAnswer() {
        StudentAnswer ans = new StudentAnswer();
        ans.setQuestionId(model.getIdQuestionList().get(questionNumber));
        if (answerType) ans.setAnsvStr(answer);
        else ans.setAnsvCheck(selectedCheck);
        return ans;
    }

    public void gotoPrev() throws IOException {
        if (model.getAllStudentAnswer().size() > questionNumber) {
            model.getAllStudentAnswer().set(questionNumber, saveAnswer());
        } else {
            model.getAllStudentAnswer().add(saveAnswer());
        }
        questionNumber--;
        initializeQuestion(questionNumber);
        if (isAnswerType()) {
            setAnswer(model.getAllStudentAnswer().get(questionNumber).getAnsvStr());
        } else {
            setSelectedCheck(model.getAllStudentAnswer().get(questionNumber).getAnsvCheck());
        }
    }

    public void gotoNext() throws IOException {
        if (model.getAllStudentAnswer().size() > questionNumber) {
            model.getAllStudentAnswer().set(questionNumber, saveAnswer());
        } else {
            model.getAllStudentAnswer().add(saveAnswer());
        }
        questionNumber++;
        initializeQuestion(questionNumber);
        if (model.getAllStudentAnswer().size() > questionNumber) {
            if (isAnswerType()) {
                setAnswer(model.getAllStudentAnswer().get(questionNumber).getAnsvStr());
            } else {
                setSelectedCheck(model.getAllStudentAnswer().get(questionNumber).getAnsvCheck());
            }
        } else {
            setAnswer(null);
            setSelectedCheck(new ArrayList<String>());
        }

    }

    public boolean isNoLast() {
        return questionNumber != model.getIdQuestionList().size() - 1;
    }

    public String gotoEnd() {
        if (model.getAllStudentAnswer().size() > questionNumber) {
            model.getAllStudentAnswer().set(questionNumber, saveAnswer());
        } else {
            model.getAllStudentAnswer().add(saveAnswer());
        }
        return "./end_test.xhtml";
    }
}
