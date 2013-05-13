package website.model.teacher;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.theoretic.ThemeService;
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
    private List<CourseDto> courses = new ArrayList<>();
    private List<ThemeDto> topics = new ArrayList<ThemeDto>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private ThemeDto changedTopic;

    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private UserModel userModel;

    public void load() {
        CourseDto courseDto = new CourseDto();
        if (courses == null || courses.isEmpty()) {
            courses = courseService.searchByOwnerId(userModel.getUser().getId());
            //courses = courseController.loadCourseList(userModel.getUser().getId());
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
        if (selectedTopic != null) {
            setChangedTopic(themeService.getById(selectedTopic));
        }
    }

    public void saveTopic() {
        if (selectedTopic != null) {

        }
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
