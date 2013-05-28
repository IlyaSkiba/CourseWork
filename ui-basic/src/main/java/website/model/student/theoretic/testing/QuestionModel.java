package website.model.student.theoretic.testing;

import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.theoretic.TheoreticTestService;
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
        return model.getQuestionNumber();
    }

    public void initializeQuestion(Integer questionId) {
        QuestionDto question = testService.getQuestion(model.getIdQuestionList().get(questionId));
        setQuestion(question.getQuestion());
        setAnswerType(question.getOpenType() == 0);
        List<AnswerDto> answerEntityList = testService.getAnswers(model.getIdQuestionList().get(questionId));
        if (!isAnswerType()) {
            List<String> checks = new ArrayList<String>();
            for (AnswerDto answer : answerEntityList) {
                checks.add(answer.getAnswer());
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
            initializeQuestion(getQuestionNumber());
        }
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswerType() {
        return answerType;
    }

    public void setAnswerType(boolean answerType) {
        this.answerType = answerType;
    }

    private StudentAnswer saveAnswer() {
        StudentAnswer ans = new StudentAnswer();
        ans.setQuestionId(model.getIdQuestionList().get(getQuestionNumber()));
        if (answerType) {
            ans.setAnsvStr(answer);
        } else {
            ans.setAnsvCheck(selectedCheck);
        }
        return ans;
    }

    public void gotoPrev() throws IOException {
        if (model.getAllStudentAnswer().size() > getQuestionNumber()) {
            model.getAllStudentAnswer().set(getQuestionNumber(), saveAnswer());
        } else {
            model.getAllStudentAnswer().add(saveAnswer());
        }
        model.setQuestionNumber(getQuestionNumber() - 1);
        initializeQuestion(getQuestionNumber());
        if (isAnswerType()) {
            setAnswer(model.getAllStudentAnswer().get(getQuestionNumber()).getAnsvStr());
        } else {
            setSelectedCheck(model.getAllStudentAnswer().get(getQuestionNumber()).getAnsvCheck());
        }
    }

    public void gotoNext() throws IOException {
        if (model.getAllStudentAnswer().size() > getQuestionNumber()) {
            model.getAllStudentAnswer().set(getQuestionNumber(), saveAnswer());
        } else {
            model.getAllStudentAnswer().add(saveAnswer());
        }
        model.setQuestionNumber(getQuestionNumber() + 1);
        initializeQuestion(getQuestionNumber());
        if (model.getAllStudentAnswer().size() > getQuestionNumber()) {
            if (isAnswerType()) {
                setAnswer(model.getAllStudentAnswer().get(getQuestionNumber()).getAnsvStr());
            } else {
                setSelectedCheck(model.getAllStudentAnswer().get(getQuestionNumber()).getAnsvCheck());
            }
        } else {
            setAnswer(null);
            setSelectedCheck(new ArrayList<String>());
        }

    }

    public boolean isNoLast() {
        return getQuestionNumber() != model.getIdQuestionList().size() - 1;
    }

    public String gotoEnd() {
        if (model.getAllStudentAnswer().size() > getQuestionNumber()) {
            model.getAllStudentAnswer().set(getQuestionNumber() , saveAnswer());
        } else {
            model.getAllStudentAnswer().add(saveAnswer());
        }
        return "./end_test.xhtml";
    }
}
