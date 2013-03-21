package website.model.admin.group;

import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.CourseGroupService;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.GroupService;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.RoleDto;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.global.admin.dto.UserGroupDto;
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
import javax.faces.context.FacesContext;
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
    private UserGroupDto groupDto;

    private GroupUsersModel users;
    private List<UserDto> teachers;
    private List<UserDto> selectedUsers;

    private List<CourseDto> systemCourses;
    private CourseGroupDto tempCourse;
    private List<CourseGroupDto> selectedCourses;
    private Map<Integer, String> courseNames;
    private Map<Integer, String> loadedUsers;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private CourseGroupService courseGroupService;

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getPartialViewContext().isPartialRequest() &&
                !context.getPartialViewContext().isExecuteAll()) {
            return;
        }
        Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();
        String groupId = requestParams.get("groupId");
        if (StringUtils.isEmpty(groupId)) {
            createNew();
        } else {
            loadFromRepository(groupId);
        }
        users = new GroupUsersModel(userService);
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
        tempCourse = new CourseGroupDto();
    }

    private void createNew() {
        groupDto = new UserGroupDto();
        selectedCourses = new ArrayList<>();
        selectedUsers = new ArrayList<>();
    }

    private void loadFromRepository(String groupId) {
        groupDto = groupService.get(Integer.parseInt(groupId));
        selectedCourses = Lists.newArrayList(courseGroupService.getCourses(groupDto));
        selectedUsers = Lists.newArrayList(groupService.getSelectedUsers(groupDto));
    }

    public UserGroupDto getGroupDto() {
        return groupDto;
    }

    public GroupUsersModel getUsers() {
        return users;
    }

    public List<CourseGroupDto> getCourses() {
        return selectedCourses;
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
        final Set<Integer> addedIds = Sets.newHashSet(Collections2.transform(selectedCourses, new Function<CourseGroupDto,
                Integer>() {
            @Override
            public Integer apply(@Nullable CourseGroupDto input) {
                return input == null ? null : input.getCourseId();
            }
        }));

        Collection<CourseDto> tempCourses = Collections2.filter(systemCourses, new Predicate<CourseDto>() {
            @Override
            public boolean apply(@Nullable CourseDto input) {
                if (input == null || StringUtils.isEmpty(courseQuery)) {
                    return StringUtils.isEmpty(courseQuery);
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
        selectedCourses.add(tempCourse);
        tempCourse = new CourseGroupDto();
    }

    public void removeCourse(CourseGroupDto courseData) {
        selectedCourses.remove(courseData);
    }

    public String save() {
        groupDto.setAssignedUserIds(Lists.transform(selectedUsers, new Function<UserDto, Integer>() {
            @Override
            public Integer apply(@Nullable UserDto input) {
                return input.getUserId();
            }
        }));
        groupService.createGroup(groupDto, selectedCourses);
        return "/admin/group/group_list.xhtml";
    }
}
