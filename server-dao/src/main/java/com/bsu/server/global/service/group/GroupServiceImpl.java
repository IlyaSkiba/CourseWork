package com.bsu.server.global.service.group;

import com.bsu.server.assembler.CourseGroupAssembler;
import com.bsu.server.assembler.GroupAssembler;
import com.bsu.server.assembler.UserAssembler;
import com.bsu.server.controller.CourseGroupController;
import com.bsu.server.controller.common.GroupController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.dto.UserGroupEntity;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.global.service.base.BaseSearchableServiceImpl;
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
public class GroupServiceImpl extends BaseSearchableServiceImpl<UserGroupDto, UserGroupEntity> implements GroupService {
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

    @Override
    public UserGroupDto create(UserGroupDto userGroup) {
        return null;
    }

    @Override
    public UserGroupDto update(UserGroupDto userGroupDto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public UserGroupDto get(Integer id) {
        return convert(groupController.getById(id));
    }

    @Transactional(readOnly = false)
    @Override
    public UserGroupDto createGroup(UserGroupDto dto, List<CourseGroupDto> courses) {
        dto.setAssignedCourseIds(Collections.<Integer>emptyList());
        UserGroupEntity entity = groupAssembler.assemble(dto);
        if (entity.getId() == null) {
            entity = groupController.create(entity);
        } else {
            entity = groupController.update(entity);
        }
        for (CourseGroupDto courseDto : courses) {
            CourseGroupEntity courseEntity = courseGroupAssembler.assemble(courseDto);
            courseEntity.setGroup(entity);
            courseGroupController.create(courseEntity);
        }
        return groupAssembler.assemble(groupController.getById(entity.getId()));
    }

    @Override
    public List<UserDto> getSelectedUsers(UserGroupDto group) {
        UserGroupEntity userGroup = groupController.getById(group.getId());
        return convertList(userGroup.getAssignedUsers(), new Function<UserAccount, UserDto>() {
            @Nullable
            @Override
            public UserDto apply(@Nullable UserAccount input) {
                return userAssembler.assemble(input);
            }
        });
    }

    @Override
    protected UserGroupDto convert(UserGroupEntity entity) {
        return groupAssembler.assemble(entity);
    }

    @Override
    protected GroupController getController() {
        return groupController;
    }
}
