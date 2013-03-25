package com.bsu.server.global.service.group;

import com.bsu.server.assembler.CourseGroupAssembler;
import com.bsu.server.assembler.GroupAssembler;
import com.bsu.server.assembler.UserAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.CourseGroupController;
import com.bsu.server.controller.common.GroupController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.dto.UserGroupEntity;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.GroupService;
import com.bsu.service.api.global.admin.dto.UserDto;
import com.bsu.service.api.global.admin.dto.UserGroupDto;
import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 27.1.13
 *         Time: 14.06
 */
@Service
@Transactional
public class GroupServiceImpl extends BaseSearchableServiceImplImpl<UserGroupDto, UserGroupEntity> implements GroupService {
    @Autowired
    private CourseGroupController courseGroupController;
    @Autowired
    private GroupController groupController;
    @Autowired
    private GroupAssembler groupAssembler;
    @Autowired
    private CourseGroupAssembler courseGroupAssembler;
    @Autowired
    private UserAssembler userAssembler;

    @Transactional(readOnly = false)
    @Override
    public UserGroupDto createGroup(UserGroupDto dto, List<CourseGroupDto> courses) {
        dto.setAssignedCourseIds(Collections.<Integer>emptyList());
        dto = super.createOrUpdate(dto);
        UserGroupEntity entity = groupController.getById(dto.getId());
        for (CourseGroupDto courseDto : courses) {
            CourseGroupEntity courseEntity = courseGroupAssembler.convert(courseDto);
            courseEntity.setGroup(entity);
            courseGroupController.create(courseEntity);
        }
        return groupAssembler.convert(groupController.getById(entity.getId()));
    }

    @Override
    public List<UserDto> getSelectedUsers(UserGroupDto group) {
        UserGroupEntity userGroup = groupController.getById(group.getId());
        return convertList(userGroup.getAssignedUsers(), new Function<UserAccount, UserDto>() {
            @Nullable
            @Override
            public UserDto apply(@Nullable UserAccount input) {
                return userAssembler.convert(input);
            }
        });
    }

    @Override
    protected GroupController getController() {
        return groupController;
    }

    @Override
    protected BaseConverter<UserGroupDto, UserGroupEntity> getConverter() {
        return groupAssembler;
    }
}
