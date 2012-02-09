package org.jgeek.website.model.student.TheoreticTesting;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 9.2.12
 * Time: 13.17
 * To change this template use File | Settings | File Templates.
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
