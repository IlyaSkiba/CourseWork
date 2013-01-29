package com.bsu.server.global.service.group;

import com.bsu.service.api.global.admin.GroupService;
import com.bsu.service.api.global.admin.dto.UserGroupDto;
import org.springframework.stereotype.Service;

/**
 * @author HomeUser
 *         Date: 27.1.13
 *         Time: 14.06
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Override
    public UserGroupDto create(UserGroupDto userGroup) {
        return null;
    }

    @Override
    public UserGroupDto update(UserGroupDto userGroupDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserGroupDto get(Long id) {
        return null;
    }
}
