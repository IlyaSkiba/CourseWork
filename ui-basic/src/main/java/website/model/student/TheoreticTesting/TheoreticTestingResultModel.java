package website.model.student.TheoreticTesting;

import com.bsu.server.theoretic.test.service.TheoreticTestService;
import com.bsu.server.theoretic.test.student.dto.StudentAnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * User: HomeUser
 * Date: 21.3.12
 * Time: 10.53
 */
@Scope("request")
@Named("theoreticResult")
public class TheoreticTestingResultModel {

    @Autowired
    private TheoreticTestingModel theoreticTestingModel;
    @Autowired
    private UserModel currentUser;
    @Autowired
    private TheoreticTestService testService;

    private int mark = 100;

    public int getMark() {
        testService.saveResults(assembleResult(), theoreticTestingModel.getIdQuestionList());
        return testService.countResult(theoreticTestingModel.getIdQuestionList(),
                currentUser.getUser().getId());

    }

    private List<StudentAnswerDto> assembleResult() {
        List<StudentAnswer> studentAnswers = theoreticTestingModel.getAllStudentAnswer();
        List<StudentAnswerDto> assembledAnswers = new ArrayList<StudentAnswerDto>(studentAnswers.size());
        for (StudentAnswer answer : studentAnswers) {
            if (answer.getAnsvCheck() != null && !answer.getAnsvCheck().isEmpty()) {
                for (String ansCheck : answer.getAnsvCheck()) {
                    StudentAnswerDto dto = new StudentAnswerDto();
                    dto.setStudent(currentUser.getUser());
                    dto.setAnswerText(ansCheck);
                    dto.setQuestion(testService.getQuestion(answer.getQuestionId()));
                    assembledAnswers.add(dto);
                }
            } else {
                if (answer.getAnsvStr() == null || answer.getAnsvStr().isEmpty()) {
                    continue;
                }
                StudentAnswerDto dto = new StudentAnswerDto();
                dto.setStudent(currentUser.getUser());
                dto.setAnswerText(answer.getAnsvStr());
                dto.setQuestion(testService.getQuestion(answer.getQuestionId()));
                assembledAnswers.add(dto);
            }
        }
        return assembledAnswers;
    }
}
