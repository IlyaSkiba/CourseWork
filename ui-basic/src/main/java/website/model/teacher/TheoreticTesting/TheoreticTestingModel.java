package website.model.teacher.TheoreticTesting;

import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.theoretic.QuestionService;
import com.bsu.service.api.theoretic.ThemeService;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 23.4.12
 * Time: 11.46
 * To change this template use File | Settings | File Templates.
 */
@Scope("session")
@Named("theoreticTeacher")
public class TheoreticTestingModel {
    private List<CourseDto> courses = new ArrayList<>();
    private List<ThemeDto> topics = new ArrayList<>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private QuestionDto newQuestion;
    private List<AnswerDto> answerList = new ArrayList<>();
    private AnswerDto tmpAnswer;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private UserModel userModel;
    @Autowired
    private QuestionService questionService;

    public void initTmpAnswer() {
        tmpAnswer = new AnswerDto();
    }

    public AnswerDto getTmpAnswer() {
        return tmpAnswer;
    }

    public void setTmpAnswer(AnswerDto tmpAnswer) {
        this.tmpAnswer = tmpAnswer;
    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getPartialViewContext().isPartialRequest() &&
                !context.getPartialViewContext().isExecuteAll()) {
            return;
        }
        selectedCourse = null;
        selectedTopic = null;
        newQuestion = new QuestionDto();
        answerList = new ArrayList<>();
    }

    public void saveQuestion() {
        newQuestion.setAnswerDtos(answerList);
        questionService.createOrUpdate(newQuestion);
    }

    public void saveAnswer() {
        boolean flag = false;

        for (int i = 0; i < answerList.size(); i++) {
            if (tmpAnswer.getId() == null || tmpAnswer.getId() == 0) {
                tmpAnswer.setId(null);
                break;
            }
            if (answerList.get(i).getId().equals(tmpAnswer.getId())) {
                answerList.remove(i);
                answerList.add(i, tmpAnswer);
                flag = true;
            }
        }
        if (!flag) {
            answerList.add(tmpAnswer);
        }
    }

    public List<AnswerDto> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerDto> answerList) {
        this.answerList = answerList;
    }

    public QuestionDto getNewQuestion() {
        return newQuestion;
    }

    public void setNewQuestion(QuestionDto newQuestion) {
        this.newQuestion = newQuestion;
    }

    public void load() {
        CourseDto courseDto = new CourseDto();
        if (courses == null || courses.isEmpty()) {
            courses = courseService.searchByOwnerId(userModel.getUser().getId());
        }
        topics.clear();
        if (selectedCourse != null) {
            courseDto.setId(selectedCourse);
            topics = themeService.getThemesForCourse(courseDto);
        }
    }

    public List<CourseDto> getCourses() {
        if (courses.isEmpty()) {
            load();
        }
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    public List<ThemeDto> getTopics() {
        load();
        return topics;
    }

    public void setSelectedCourse(Integer selectedCourse) {
        this.selectedCourse = selectedCourse;
        selectedTopic = null;
    }

    public Integer getSelectedCourse() {
        return selectedCourse;
    }

    public Integer getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(Integer selectedTopic) {
        this.selectedTopic = selectedTopic;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
