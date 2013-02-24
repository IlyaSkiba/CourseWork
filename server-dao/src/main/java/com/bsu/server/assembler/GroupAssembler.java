package com.bsu.server.assembler;

import com.bsu.server.controller.CourseGroupController;
import com.bsu.server.controller.UserController;
import com.bsu.server.controller.common.GroupController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.dto.UserGroupEntity;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.service.api.global.admin.dto.UserGroupDto;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collections;

/**
 * @author HomeUser
 *         Date: 24.2.13
 *         Time: 17.32
 */
@Component
public class GroupAssembler {
    @Autowired
    private GroupController groupController;
    @Autowired
    private UserController userController;
    @Autowired
    private CourseGroupController courseController;

    public UserGroupDto assemble(UserGroupEntity groupEntity) {
        UserGroupDto dto = new UserGroupDto();
        dto.setId(groupEntity.getId());
        dto.setGroupName(groupEntity.getGroupName());
        dto.setAssignedCourseIds(Lists.transform(groupEntity.getCourses(), new Function<CourseGroupEntity, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable CourseGroupEntity input) {
                return input.getId();
            }
        }));
        dto.setAssignedUserIds(Lists.transform(groupEntity.getAssignedUsers(), new Function<UserAccount, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable UserAccount input) {
                return input.getId();
            }
        }));
        return dto;
    }

    public UserGroupEntity assemble(UserGroupDto dto) {
        UserGroupEntity entity;
        if (dto.getId() != null) {
            entity = groupController.getById(dto.getId());
            if (entity == null) {
                entity = new UserGroupEntity();
            }
        } else {
            entity = new UserGroupEntity();
        }
        entity.setGroupName(dto.getGroupName());
        entity.setCourses(Lists.transform(dto.getAssignedCourseIds() != null ? dto.getAssignedUserIds()
                : Collections.<Integer>emptyList(), new Function<Integer, CourseGroupEntity>() {
            @Nullable
            @Override
            public CourseGroupEntity apply(@Nullable Integer input) {
                return courseController.getById(input);
            }
        }));

        entity.setAssignedUsers(Lists.transform(dto.getAssignedUserIds() != null ? dto.getAssignedUserIds() :
                Collections.<Integer>emptyList(), new Function<Integer, UserAccount>() {
            @Nullable
            @Override
            public UserAccount apply(@Nullable Integer input) {
                return userController.getById(input);
            }
        }));
        return entity;
    }

}
