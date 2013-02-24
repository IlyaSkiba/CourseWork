package com.bsu.server.assembler;

import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.CourseGroupController;
import com.bsu.server.controller.UserController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.service.api.dto.CourseGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.46
 */
@Service
public class CourseGroupAssembler {
    @Autowired
    private CourseGroupController courseGroupController;
    @Autowired
    private CourseController courseController;
    @Autowired
    private UserController userController;

    public CourseGroupDto assemble(CourseGroupEntity entity) {
        CourseGroupDto dto = new CourseGroupDto();
        dto.setCourseId(entity.getAssignedCourse().getId());
        dto.setGroupId(entity.getGroup().getId());
        dto.setGroupName(entity.getGroup().getGroupName());
        dto.setOwnerId(entity.getGroupOwner().getId());
        dto.setId(entity.getId());
        return dto;
    }

    public CourseGroupEntity assemble(CourseGroupDto dto) {
        CourseGroupEntity entity;
        if (dto.getId() != null) {
            entity = courseGroupController.getById(dto.getId());
        } else {
            entity = new CourseGroupEntity();
            entity.setId(dto.getId());
        }
        entity.setAssignedCourse(courseController.getById(dto.getCourseId()));
        //todo: fix this
        entity.setGroup(null);
        entity.setGroupOwner(userController.getById(dto.getId()));
        return entity;
    }
}
