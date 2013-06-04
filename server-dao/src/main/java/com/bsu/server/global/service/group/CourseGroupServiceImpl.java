package com.bsu.server.global.service.group;

import com.bsu.server.assembler.CourseGroupAssembler;
import com.bsu.server.assembler.UserAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.CourseGroupController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.CourseGroupService;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.global.admin.dto.UserGroupDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.45
 */
@Service
@Transactional
public class CourseGroupServiceImpl extends BaseSearchableServiceImplImpl<CourseGroupDto,
        CourseGroupEntity> implements CourseGroupService {
    @Autowired
    private CourseGroupAssembler courseGroupAssembler;
    @Autowired
    private CourseGroupController courseGroupController;
    @Autowired
    private UserAssembler userAssembler;

    @Override
    protected BaseController<CourseGroupEntity> getController() {
        return courseGroupController;
    }

    @Override
    protected BaseConverter<CourseGroupDto, CourseGroupEntity> getConverter() {
        return courseGroupAssembler;
    }

    @Override
    public List<CourseGroupDto> getAssignedCourse(Integer courseId) {
        return convertList(courseGroupController.getAssignedCourses(courseId));
    }

    @Override
    public List<CourseGroupDto> getCourses(UserGroupDto groupDto) {
        return convertList(courseGroupController.getCoursesForGroup(groupDto.getId()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsersForCourse(CourseDto courseDto) {
        List<CourseGroupEntity> entities = courseGroupController.getGroupsForCourse(courseDto.getId());
        Map<Integer, UserDto> users = Maps.newHashMap();
        for (CourseGroupEntity courseGroupEntity : entities) {
            for (UserAccount usersInGroup : courseGroupEntity.getGroup().getAssignedUsers()) {
                users.put(usersInGroup.getId(), userAssembler.convert(usersInGroup));
            }
        }
        return Lists.newArrayList(users.values());
    }

    @Override
    public void updateAssignation(List<CourseGroupDto> courses) {
        for (CourseGroupDto course : courses) {
            CourseGroupEntity entity = courseGroupAssembler.convert(course);
            if (course.getId() == null) {
                courseGroupController.create(entity);
            } else {
                courseGroupController.update(entity);
            }
        }

    }
}
