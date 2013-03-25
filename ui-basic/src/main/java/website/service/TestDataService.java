package website.service;

import com.bsu.server.assembler.RoleAssembler;
import com.bsu.server.controller.RoleController;
import com.bsu.server.dto.security.UserRole;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.HashMap;
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
    private Map<String, UserRole> roles = new HashMap<>();
    private Map<String, UserDto> users = new HashMap<>();

    public void generateTestData() {
        createRoles();
        createUsers();
        createCourses();
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
            courseDto.setOwnerId(users.get("teacher" + i).getUserId());
            courseService.createOrUpdate(courseDto);
        }
    }
}
