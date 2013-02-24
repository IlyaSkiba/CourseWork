package com.bsu.server.global.service.group;

import com.bsu.server.assembler.CourseGroupAssembler;
import com.bsu.server.controller.CourseGroupController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.global.service.base.BaseSearchableServiceImpl;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.CourseGroupService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.45
 */
@Service
public class CourseGroupServiceImpl extends BaseSearchableServiceImpl<CourseGroupDto,
        CourseGroupEntity> implements CourseGroupService {
    @Autowired
    private CourseGroupAssembler courseGroupAssembler;
    @Autowired
    private CourseGroupController courseGroupController;

    @Override
    protected CourseGroupDto convert(CourseGroupEntity entity) {
        return courseGroupAssembler.assemble(entity);
    }

    @Override
    protected BaseController<CourseGroupEntity> getController() {
        return courseGroupController;
    }

    @Override
    public List<CourseGroupDto> getAssignedCourse(Integer courseId) {
        return Lists.transform(courseGroupController.getAssignedCourses(courseId),
                new TransformFunction());
    }

    @Override
    public void updateAssignation(List<CourseGroupDto> courses) {
        for (CourseGroupDto course : courses) {
            CourseGroupEntity entity = courseGroupAssembler.assemble(course);
            if (course.getId() == null) {
                courseGroupController.create(entity);
            } else {
                courseGroupController.update(entity);
            }
        }

    }
}
