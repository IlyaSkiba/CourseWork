package website.model.lecturer.theoretic.testing;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 22.5.12
 * Time: 14.26
 * To change this template use File | Settings | File Templates.
 */
public class ApproveQuestionsTable {
    private String teacher;
    private Date data;
    private String questionText = "Здесь должен быть текст вопроса.";

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTeacher() {

        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getQuestionText() {
        return questionText;
    }
    //@todo:переделать на сервисы
}
