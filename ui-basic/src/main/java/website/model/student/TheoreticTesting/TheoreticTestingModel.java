package website.model.student.TheoreticTesting;

import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.dto.CourseDto;
import com.bsu.server.dto.ThemeDto;
import com.bsu.server.theoretic.test.service.TheoreticTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

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
    private static final String TEMPLATE_URL = "teoretic_main.xhtml";
    private List<CourseDto> courses = new ArrayList<CourseDto>();
    private List<ThemeDto> topics = new ArrayList<ThemeDto>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private List<StudentAnswer> allStudentAnswer;
    private List<Integer> idQuestionList;
    @Autowired
    private CourseController courseController;
    @Autowired
    private ThemeController themeController;
    @Autowired
    private TheoreticTestService testService;

    public List<StudentAnswer> getAllStudentAnswer() {
        return allStudentAnswer;
    }

    public void load() {
        courses = courseController.loadCourseList();
        topics.clear();
        if (selectedCourse != null) {
            topics = themeController.getThemesForCourse(selectedCourse);
        }
    }

    public List<CourseDto> getCourses() {
        if (courses.isEmpty()) load();
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

    public void gotoTest() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("theoretic/test.xhtml");
        idQuestionList = testService.getQuestionIds(selectedTopic);
        allStudentAnswer = new ArrayList<StudentAnswer>(idQuestionList.size());
    }

    public List<Integer> getIdQuestionList() {
        return idQuestionList;
    }
}