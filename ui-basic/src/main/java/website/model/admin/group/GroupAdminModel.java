package website.model.admin.group;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author HomeUser
 *         Date: 29.1.13
 *         Time: 23.20
 */
@Named
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GroupAdminModel implements Serializable {
    private String name;
    private GroupUsersModel users;
    private List<CourseGroupDto> courses;
    private Map<Integer, String> courseNames;
    private Map<Integer, String> loadedUsers;
    private List<UserDto> teachers;
    private List<UserDto> selectedUsers;
    private List<CourseDto> systemCourses;
    private CourseGroupDto tempCourse;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @PostConstruct
    public void init() {
        users = new GroupUsersModel(userService);
        courses = new ArrayList<>();
        courseNames = new HashMap<>();
        loadedUsers = new HashMap<>();
        systemCourses = courseService.getCourses();
        for (CourseDto course : systemCourses) {
            courseNames.put(course.getId(), course.getCourseName());
        }
        RoleDto role = new RoleDto();
        role.setName("ROLE_TEACHER");
        teachers = userService.getUsersByRoles(Sets.newHashSet(role));
        for (UserDto user : teachers) {
            loadedUsers.put(user.getUserId(), user.getUsername());
        }
        selectedUsers = new ArrayList<>();
        tempCourse = new CourseGroupDto();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupUsersModel getUsers() {
        return users;
    }

    public List<CourseGroupDto> getCourses() {
        return courses;
    }

    public List<UserDto> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<UserDto> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public String resolveUserName(Integer id) {
        return loadedUsers.get(id);
    }

    public String resolveCourseName(Integer id) {
        return courseNames.get(id);
    }

    public CourseGroupDto getTempCourse() {
        return tempCourse;
    }

    public void setTempCourse(CourseGroupDto tempCourse) {
        this.tempCourse = tempCourse;
    }

    public List<CourseDto> filterCourses(final String courseQuery) {
        final Set<Integer> addedIds = Sets.newHashSet(Collections2.transform(courses, new Function<CourseGroupDto,
                Integer>() {
            @Override
            public Integer apply(@Nullable CourseGroupDto input) {
                return input.getCourseId();
            }
        }));

        Collection<CourseDto> tempCourses = Collections2.filter(systemCourses, new Predicate<CourseDto>() {
            @Override
            public boolean apply(@Nullable CourseDto input) {
                if (input == null || StringUtils.isEmpty(courseQuery)) {
                    return false;
                }
                return !addedIds.contains(input.getId())
                        && input.getCourseName().toLowerCase().startsWith(courseQuery.toLowerCase());
            }
        });
        return Lists.newArrayList(tempCourses);
    }

    public List<UserDto> filterUsers(final String userQuery) {
        Collection<UserDto> users = Collections2.filter(teachers, new Predicate<UserDto>() {
            @Override
            public boolean apply(@Nullable UserDto input) {
                return input != null && (StringUtils.isEmpty(userQuery)
                        || input.getUsername().toLowerCase().startsWith(userQuery.toLowerCase().toLowerCase()));
            }
        });
        return Lists.newArrayList(users);
    }

    public void addCourse() {
        courses.add(tempCourse);
        tempCourse = new CourseGroupDto();
    }
}
