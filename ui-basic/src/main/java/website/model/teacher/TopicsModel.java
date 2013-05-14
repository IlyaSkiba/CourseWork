package website.model.teacher;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.theoretic.ThemeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.global.UserModel;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: HomeUser
 * Date: 23.4.12
 * Time: 11.46
 */
@Scope("session")
@Named("topicsChange")
public class TopicsModel {
    private List<CourseDto> courses = new ArrayList<>();
    private List<ThemeDto> topics = new ArrayList<>();
    private List<ThemeDto> topicParents = new ArrayList<>();
    private Integer selectedCourse;
    private Integer selectedTopic;
    private ThemeDto changedTopic;
    private boolean create = false;

    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private UserModel userModel;

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getPartialViewContext().isPartialRequest() &&
                !context.getPartialViewContext().isExecuteAll()) {
            return;
        }
        selectedCourse = null;
        selectedTopic = null;
        create = false;
        topicParents = null;
        Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();
        String formType = requestParams.get("formType");
        if (StringUtils.equals(formType,"create")) {
            create = true;
            changedTopic = new ThemeDto();
        }
    }

    public List<ThemeDto> getTopicParents() {
        return topicParents;
    }

    public void setTopicParents(List<ThemeDto> topicParents) {
        this.topicParents = topicParents;
    }

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
        changedTopic.setCreatorName(userModel.getUserName());
        changedTopic.setCourseId(selectedCourse);
        themeService.createOrUpdate(changedTopic);
    }

    public ThemeDto getChangedTopic() {
        return changedTopic;
    }

    public void setChangedTopic(ThemeDto changedTopic) {
        this.changedTopic = changedTopic;
    }

    public void deleteTopic() {
        changedTopic.setCreatorName(userModel.getUserName());
        themeService.delete(changedTopic);
    }

}
