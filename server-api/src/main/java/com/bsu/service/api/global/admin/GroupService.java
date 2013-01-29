package com.bsu.service.api.global.admin;

import com.bsu.service.api.global.admin.dto.UserGroupDto;

/**
 * @author HomeUser
 *         Date: 27.1.13
 *         Time: 13.52
 */
public interface GroupService {

    public UserGroupDto create(UserGroupDto userGroup);

    public UserGroupDto update(UserGroupDto userGroupDto);

    public void delete(Long id);

    public UserGroupDto get(Long id);
}
