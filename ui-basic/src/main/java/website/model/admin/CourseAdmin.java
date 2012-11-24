package website.model.admin;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import website.model.admin.common.AdminUserModel;

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
    @Autowired
    private CourseAdminModel currentModel;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    public void create() {
        currentModel.clear();
    }

    public void open(String id) {
        Set<AdminUserModel> users = getPossibleUsers();
        final CourseDto dto = courseService.getCourse(Integer.valueOf(id));
        currentModel.setId(id);
        currentModel.setName(dto.getCourseName());
        currentModel.setOwner(dto.getOwnerId().toString());
    }

    public void save() throws IOException {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(StringUtils.isEmpty(currentModel.getId()) ? null : Integer.parseInt(currentModel.getId()));
        courseDto.setCourseName(currentModel.getName());
        courseDto.setOwnerId(StringUtils.isEmpty(currentModel.getOwner()) ? null
                : Integer.valueOf(currentModel.getOwner()));
        if (courseDto.getId() == null) {
            courseDto.setThemeIds(Lists.<Integer>newArrayList());
            courseService.createCourse(courseDto);
        } else {
            courseService.updateCourse(courseDto);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/courses.xhtml");
    }

    public Set<AdminUserModel> getPossibleUsers() {
        List<UserDto> users = userService.getTeachers();
        Set<AdminUserModel> resultSet = new HashSet<>();
        for (UserDto userDto : users) {
            AdminUserModel model = new AdminUserModel();
            model.setId(userDto.getUserId().toString());
            model.setFirstName(userDto.getFirstName());
            model.setMiddleName(userDto.getMiddleName());
            model.setLastName(userDto.getLastName());
            resultSet.add(model);
        }
        return resultSet;
    }
}
