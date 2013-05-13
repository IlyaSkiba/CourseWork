package website.model.teacher.TheoreticTesting;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.CourseService;
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
    private String value;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private UserModel userModel;

    public void load() {
        if (courses == null || courses.isEmpty()) {
            courses = courseService.loadCourseList(userModel.getUser());
        }
        topics.clear();
        if (selectedCourse != null) {
            topics = themeService.getThemesForCourse(selectedCourse, userModel.getUser());
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
