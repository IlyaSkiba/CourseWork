package website.model.student.theoretic.testing;

import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.dto.CourseEntity;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.theoretic.test.service.TheoreticTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 24.1.12
 * Time: 18.54
 */

@Scope("session")
@Named("theoretic")
public class TheoreticTestingModel {
    private static final String STUDENT_FOLDER = "/student/";
    private static final String TEMPLATE_URL = "teoretic_main.xhtml";
    private List<CourseEntity> courses = new ArrayList<CourseEntity>();
    private List<ThemeEntity> topics = new ArrayList<ThemeEntity>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private List<StudentAnswer> allStudentAnswer;
    private List<Integer> idQuestionList;
    private Integer testId;
    @Autowired
    private CourseController courseController;
    @Autowired
    private ThemeController themeController;
    @Autowired
    private TheoreticTestService testService;

    private int cheaterState;

    @Autowired
    private UserModel userModel;

    public List<StudentAnswer> getAllStudentAnswer() {
        return allStudentAnswer;
    }

    public void cleanup() {
        selectedCourse = null;
        selectedTopic = null;
        courses.clear();
        topics.clear();
        testId = null;
        allStudentAnswer = null;
        idQuestionList = null;
    }

    public String initialize() {
        cleanup();
        load();
        return STUDENT_FOLDER + TEMPLATE_URL;
    }

    public void load() {

        if (courses == null || courses.isEmpty()) {
            courses = courseController.loadCourseList(userModel.getUser().getId());
        }
        topics.clear();
        if (selectedCourse != null) {
            topics = themeController.getThemesForCourse(selectedCourse, userModel.getUser().getId());
        }
    }

    public List<CourseEntity> getCourses() {
        if (courses.isEmpty()) {
            load();
        }
        return courses;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }

    public List<ThemeEntity> getTopics() {
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
        testId = testService.getTestId(selectedTopic, userModel.getUser().getId());
    }

    public void gotoTest() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("theoretic/test.xhtml");
        idQuestionList = testService.getQuestionIds(selectedTopic);
        allStudentAnswer = new ArrayList<StudentAnswer>(idQuestionList.size());
    }

    public void redirectToStatistic() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./statistic/teoretic_stat.xhtml");
    }

    public List<Integer> getIdQuestionList() {
        return idQuestionList;
    }

    public Integer getTestId() {
        return testId;
    }

    public int getCheaterState() {
        return cheaterState;
    }

    public void setCheaterState(int cheaterState) {
        this.cheaterState = cheaterState;
    }
}