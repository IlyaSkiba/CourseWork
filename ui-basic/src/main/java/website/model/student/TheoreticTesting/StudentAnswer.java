package website.model.student.TheoreticTesting;

import java.util.List;

/**
 * User: HomeUser
 * Date: 9.2.12
 * Time: 13.17
 */
class StudentAnswer {
    private List<String> ansvCheck;
    private String ansvStr;
    private Integer questionId;

    public String getAnsvStr() {
        return ansvStr;
    }

    public void setAnsvStr(String ansvStr) {
        this.ansvStr = ansvStr;
    }

    public List<String> getAnsvCheck() {

        return ansvCheck;
    }

    public void setAnsvCheck(List<String> ansvCheck) {
        this.ansvCheck = ansvCheck;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
