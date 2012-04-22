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
}
