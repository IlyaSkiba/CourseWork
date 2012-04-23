package website.model.teacher;

import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.dto.CourseDto;
import com.bsu.server.dto.ThemeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

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
@Named("topicsChange")
public class TopicsModel {
    private List<CourseDto> courses = new ArrayList<CourseDto>();
    private List<ThemeDto> topics = new ArrayList<ThemeDto>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private ThemeDto changedTopic;
    @Autowired
    private CourseController courseController;
    @Autowired
    private ThemeController themeController;

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
        if (selectedTopic != null)
            setChangedTopic(themeController.getTheme(selectedTopic));
    }

    public void saveTopic() {
        /**@todo сохранить созданную тему
         * @todo сохранить измененную тему
         */
    }

    public ThemeDto getChangedTopic() {
        return changedTopic;
    }

    public void setChangedTopic(ThemeDto changedTopic) {
        this.changedTopic = changedTopic;
    }

    public void deleteTopic() {
        // @todo удалить тему
    }

}
