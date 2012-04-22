package website.model.student.TheoreticTesting;

import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.dto.CourseDto;
import org.springframework.context.annotation.Scope;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

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
    private List<String> courses = new ArrayList<String>();
    private Map<String, List<String>> topics = new HashMap<String, List<String>>();
    private String selectedCourse;
    private String selectedTopic;
    private List<StudentAnswer> allStudentAnswer;
    private List<Integer> idQuestionList;
    @Inject
    private CourseController courseController;
    @Inject
    private ThemeController themeController;

    public List<StudentAnswer> getAllStudentAnswer() {
        return allStudentAnswer;
    }

    //@TODO: переделать под сервисы
    public void load() {
        List<CourseDto> courseDtoList = courseController.loadCourseList();
        if (courses == null) {
            courses = new ArrayList<String>();
        } else courses.clear();
        for (CourseDto courseDto : courseDtoList) {
            courses.add(courseDto.getCourseName());
        }
        topics.clear();
        for (String course : courses) {
            topics.put(course, Arrays.asList(course + "->Topic1", course + "->Topic2"));
        }
    }

    public List<String> getCourses() {
        load();
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public List<String> getTopics() {
        load();
        return topics.get(selectedCourse);
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
        selectedTopic = null;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public String getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(String selectedTopic) {
        this.selectedTopic = selectedTopic;
    }

    public void gotoTest() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("theoretic/test.xhtml");
        //@TODO: загрузить список всех id теста
        idQuestionList = Arrays.asList(1, 2, 3);
        allStudentAnswer = new ArrayList<StudentAnswer>(idQuestionList.size());
    }

    public List<Integer> getIdQuestionList() {
        return idQuestionList;
    }
}