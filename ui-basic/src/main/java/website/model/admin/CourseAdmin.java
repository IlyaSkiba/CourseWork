package website.model.admin;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import website.model.admin.common.AdminUserModel;
import website.model.common.ServletUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ilya SKiba
 * @created 16/11/12
 */
@Named
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CourseAdmin {
    @Qualifier("courseAdminModel")
    @Autowired
    private CourseAdminModel currentModel;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    private CourseDto courseEdit;
    private List<CourseDto> courses;

    @PostConstruct
    public void init() {
        courses = courseService.getCourses();
    }


    public void create() {
        currentModel.clear();
    }

    public void save() throws IOException {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(StringUtils.isEmpty(currentModel.getId()) ? null : Integer.parseInt(currentModel.getId()));
        courseDto.setCourseName(currentModel.getName());
        courseDto.setOwnerId(StringUtils.isEmpty(currentModel.getOwner()) ? null
                : Integer.valueOf(currentModel.getOwner()));
        if (courseDto.getId() == null) {
            courseDto.setThemeIds(Lists.<Integer>newArrayList());
            courseService.createOrUpdate(courseDto);
        } else {
            courseService.createOrUpdate(courseDto);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect(
                ServletUtils.buildPath("/admin/course/course_list.xhtml"));
    }

    public Set<AdminUserModel> getPossibleUsers() {
        List<UserDto> users = userService.getUsersByRoles(Sets.<RoleDto>newHashSet(new RoleDto() {{
            setName("ROLE_TEACHER");
        }}));
        Set<AdminUserModel> resultSet = new HashSet<>();
        for (UserDto userDto : users) {
            AdminUserModel model = new AdminUserModel();
            model.setId(userDto.getUserId());
            model.setFirstName(userDto.getFirstName());
            model.setMiddleName(userDto.getMiddleName());
            model.setLastName(userDto.getLastName());
            resultSet.add(model);
        }
        return resultSet;
    }

    public String encodeName(Integer userId) {
        if (userId == null) {
            return StringUtils.EMPTY;
        }
        Set<AdminUserModel> users = getPossibleUsers();
        for (AdminUserModel user : users) {
            if (user.getId().equals(userId)) {
                return user.getDisplayName();
            }
        }
        return userId.toString();
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public CourseDto getCourseEdit() {
        return courseEdit == null ? new CourseDto() : courseEdit;
    }

    public void setCourseEdit(CourseDto courseEdit) {
        this.courseEdit = courseEdit;
    }

    public void editCourse() {
        CourseDto saveDto = getCourseEdit();
        saveDto.setThemeIds(courseService.getById(saveDto.getId()).getThemeIds());
        courseService.createOrUpdate(saveDto);
        courses = courseService.getCourses();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("", String.format("Курс '%s' обновлен", saveDto.getCourseName())));
    }

    public void deleteCourse() {
        CourseDto saveDto = getCourseEdit();
        courseService.delete(saveDto);
        courses = courseService.getCourses();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("", String.format("Курс '%s' удален", saveDto.getCourseName())));
    }
}
