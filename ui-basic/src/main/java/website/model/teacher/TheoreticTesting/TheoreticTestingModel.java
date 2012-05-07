package website.model.teacher.TheoreticTesting;

import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.dto.CourseDto;
import com.bsu.server.dto.ThemeDto;
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
    private List<CourseDto> courses = new ArrayList<CourseDto>();
    private List<ThemeDto> topics = new ArrayList<ThemeDto>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private String value;
    @Autowired
    private CourseController courseController;
    @Autowired
    private ThemeController themeController;
    @Autowired
    private UserModel userModel;

    public void load() {
        if (courses == null || courses.isEmpty()) {
            courses = courseController.loadCourseList(userModel.getUser().getId());
        }
        topics.clear();
        if (selectedCourse != null) {
            topics = themeController.getThemesForCourse(selectedCourse, userModel.getUser().getId());
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
