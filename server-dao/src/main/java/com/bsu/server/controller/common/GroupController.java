package com.bsu.server.controller.common;

import com.bsu.server.dto.UserGroupEntity;
import org.springframework.stereotype.Component;

/**
 * @author HomeUser
 *         Date: 24.2.13
 *         Time: 17.35
 */
@Component
public class GroupController extends BaseController<UserGroupEntity> {
    @Override
    protected Class<UserGroupEntity> getEntityClass() {
        return UserGroupEntity.class;
    }
}
