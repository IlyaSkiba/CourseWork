package website.model.teacher;

import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.dto.CourseEntity;
import com.bsu.server.dto.ThemeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * User: HomeUser
 * Date: 23.4.12
 * Time: 11.46
 */
@Scope("session")
@Named("topicsChange")
public class TopicsModel {
    private List<CourseEntity> courses = new ArrayList<CourseEntity>();
    private List<ThemeEntity> topics = new ArrayList<ThemeEntity>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private ThemeEntity changedTopic;
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
        if (selectedTopic != null) {
            setChangedTopic(themeController.getTheme(selectedTopic));
        }
    }

    public void saveTopic() {
        /**@todo сохранить созданную тему
         * @todo сохранить измененную тему
         */
    }

    public ThemeEntity getChangedTopic() {
        return changedTopic;
    }

    public void setChangedTopic(ThemeEntity changedTopic) {
        this.changedTopic = changedTopic;
    }

    public void deleteTopic() {
        // @todo удалить тему
    }

}
