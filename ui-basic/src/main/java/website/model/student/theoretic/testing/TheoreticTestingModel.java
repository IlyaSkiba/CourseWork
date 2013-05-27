package website.model.student.theoretic.testing;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.theoretic.ThemeService;
import com.bsu.service.api.theoretic.TheoreticTestService;
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
    private List<CourseDto> courses = new ArrayList<>();
    private List<ThemeDto> topics = new ArrayList<>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private Integer significance = 1;
    private List<StudentAnswer> allStudentAnswer;
    private List<Integer> idQuestionList;
    private Integer testId;
    private ThemeDto currentTopic;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
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
        currentTopic = null;
    }

    public String initialize() {
        cleanup();
        load();
        return STUDENT_FOLDER + TEMPLATE_URL;
    }

    public void load() {

        if (courses == null || courses.isEmpty()) {
            courses = courseService.loadCourseList(userModel.getUser());
        }
        topics.clear();
        if (selectedCourse != null) {
            topics = themeService.getThemesForCourse(selectedCourse, userModel.getUser(), significance);
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
        testId = testService.getTestId(selectedTopic, userModel.getUser().getId());
        currentTopic = themeService.getById(selectedTopic);
    }

    public void gotoTest() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("theoretic/test.xhtml");
        idQuestionList = testService.getQuestionIds(selectedTopic);
        allStudentAnswer = new ArrayList<>(idQuestionList.size());
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

    public Integer getSignificance() {
        return significance;
    }

    public void setSignificance(Integer significance) {
        this.significance = significance;
    }

    public ThemeDto getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(ThemeDto currentTopic) {
        this.currentTopic = currentTopic;
    }
}