package website.model.student.Statistic;

import org.springframework.context.annotation.Scope;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 7.5.12
 * Time: 14.30
 * To change this template use File | Settings | File Templates.
 */
@Scope("session")
public class StatisticTable {
    private String test;
    private int result;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
