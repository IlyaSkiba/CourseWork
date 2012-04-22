package website.model.student.TheoreticTesting;

import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * User: HomeUser
 * Date: 6.2.12
 * Time: 15.36
 */
@Scope("session")
@Named()
public class QuestionModel {
    @Inject
    private TheoreticTestingModel model;
    private String question = "First Question";
    private boolean answerType = false;
    private String answer;
    private List<String> selectedCheck;
    private List<String> allCheck;

    public int getQuestionNumber() {
        return questionNumber;
    }

    private int questionNumber = 0;

    public List<String> getSelectedCheck() {
        return selectedCheck;
    }

    public void setSelectedCheck(List<String> selectedCheck) {
        this.selectedCheck = selectedCheck;
    }

    private void load() {
        allCheck = Arrays.asList("Ans1", "Ans2", "Ans3", "Ans4");
    }

    public List<String> getAllCheck() {
        load();
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
        if (answerType) ans.setAnsvStr(answer);
        else ans.setAnsvCheck(selectedCheck);
        return ans;
    }

    public void gotoPrev() throws IOException {
        questionNumber--;
        model.getAllStudentAnswer().set(questionNumber, saveAnswer());
        //@TODO:загрузить новый вопрос и ответы
    }

    public void gotoNext() throws IOException {
        if (model.getAllStudentAnswer().size() != questionNumber) {
            model.getAllStudentAnswer().set(questionNumber, saveAnswer());
        } else {
            model.getAllStudentAnswer().add(saveAnswer());
        }
        questionNumber++;
        //@TODO:загрузить новый вопрос и ответы
    }

    public boolean isNoLast() {
        if (questionNumber == model.getIdQuestionList().size())
            return false;
        else return true;
    }

    public String gotoEnd() {
        return "./end_test.xhtml";
    }
}
