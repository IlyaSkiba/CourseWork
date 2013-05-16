package com.bsu.server;

import com.bsu.server.assembler.RoleAssembler;
import com.bsu.server.controller.RoleController;
import com.bsu.server.dto.security.UserRole;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.GroupService;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.global.admin.dto.UserGroupDto;
import com.bsu.service.api.theoretic.ThemeService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HomeUser
 *         Date: 19.2.13
 *         Time: 22.16
 */
@Component
@Named
public class TestDataService {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RoleController roleController;
    @Autowired
    private RoleAssembler roleAssembler;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private GroupService groupService;

    private Map<String, UserRole> roles = new HashMap<>();
    private Map<String, UserDto> users = new HashMap<>();

    public void generateTestData() {
        createRoles();
        createUsers();
        createCourses();
        createThemes();
        createGroups();
    }

    private void createRoles() {
        UserRole role = new UserRole();
        role.setRoleName("ROLE_STUDENT");
        roles.put("ROLE_STUDENT", roleController.create(role));

        role = new UserRole();
        role.setRoleName("ROLE_ADMIN");
        roles.put("ROLE_ADMIN", roleController.create(role));

        role = new UserRole();
        role.setRoleName("ROLE_TEACHER");
        roles.put("ROLE_TEACHER", roleController.create(role));
    }

    private void createUsers() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setFirstName("admin");
        userDto.setLastName("admin");
        userDto.setRoles(roleAssembler.assemble(roles.get("ROLE_ADMIN")));
        users.put("admin", userService.createOrUpdate(userDto));

        for (int i = 0; i < 20; i++) {
            userDto.setUsername("student" + i);
            userDto.setFirstName("student" + i);
            userDto.setLastName("student" + i);
            userDto.setRoles(roleAssembler.assemble(roles.get("ROLE_STUDENT")));
            users.put("student" + i, userService.createOrUpdate(userDto));
        }

        for (int i = 0; i < 20; i++) {
            userDto.setUsername("teacher" + i);
            userDto.setFirstName("teacher" + i);
            userDto.setLastName("teacher" + i);
            userDto.setRoles(roleAssembler.assemble(roles.get("ROLE_TEACHER")));
            users.put("teacher" + i, userService.createOrUpdate(userDto));
        }
    }

    private void createCourses() {

        for (int i = 0; i < 20; i++) {
            CourseDto courseDto = new CourseDto();
            courseDto.setCourseName("CourseName" + i);
            courseDto.setOwnerId(users.get("teacher" + i).getId());
            courseService.createOrUpdate(courseDto);
        }
    }

    private void createThemes() {
        for (CourseDto course : courseService.getCourses()) {
            for (int i = 1; i <= 3; i++) {
                ThemeDto dto = new ThemeDto();
                dto.setCourseId(course.getId());
                dto.setName("ThemeName" + i);
                dto.setSignificance(i);
                dto.setCreatorName(userService.getById(course.getOwnerId()).getUsername());
                themeService.createOrUpdate(dto);
            }
        }
    }

    private void createGroups() {
        for (int i = 0; i < 5; i++) {
            final UserGroupDto dto = new UserGroupDto();
            dto.setGroupName("Group" + i);
            List<Integer> userIds = new ArrayList<>();
            for (UserDto s : users.values()) {
                userIds.add(s.getId());
            }
            dto.setAssignedUserIds(userIds);
            groupService.createGroup(dto, Lists.transform(courseService.getCourses(), new Function<CourseDto, CourseGroupDto>() {
                @Override
                public CourseGroupDto apply(CourseDto courseDto) {
                    CourseGroupDto resDto = new CourseGroupDto();
                    resDto.setGroupName(dto.getGroupName());
                    resDto.setCourseId(courseDto.getId());
                    resDto.setOwnerId(courseDto.getOwnerId());
                    return resDto;
                }
            }));
        }
    }
}
