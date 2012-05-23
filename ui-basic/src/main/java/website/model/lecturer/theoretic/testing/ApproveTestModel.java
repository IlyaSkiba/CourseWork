package website.model.lecturer.theoretic.testing;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 22.5.12
 * Time: 14.20
 * To change this template use File | Settings | File Templates.
 */
@Scope("session")
@Named
public class ApproveTestModel {
    private List<ApproveQuestionsTable> proposedQuestions;

    public String init() {
        proposedQuestions = new ArrayList<ApproveQuestionsTable>();
        ApproveQuestionsTable qTable = new ApproveQuestionsTable();
        qTable.setTeacher("Teacher 1");
        qTable.setData(Calendar.getInstance().getTime());
        proposedQuestions.add(qTable);
        qTable = new ApproveQuestionsTable();
        qTable.setTeacher("Teacher 2");
        qTable.setData(Calendar.getInstance().getTime());
        proposedQuestions.add(qTable);
        qTable = new ApproveQuestionsTable();
        qTable.setTeacher("Teacher 3");
        qTable.setData(Calendar.getInstance().getTime());
        proposedQuestions.add(qTable);
        return "/lecturer/teoretic_approve.xhtml";
    }

    public List<ApproveQuestionsTable> getProposedQuestions() {
        return proposedQuestions;
    }

    public void approveQuestion() {

    }

    public void rejectQuestion() {

    }

    public void commentQuestion() {

    }
    //@todo: принять, отклонить, комментировать вопрос
}
