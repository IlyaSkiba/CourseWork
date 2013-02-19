package com.bsu.server.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.security.UserRole;
import org.springframework.stereotype.Service;

/**
 * @author HomeUser
 *         Date: 27.12.12
 *         Time: 20.27
 */
@Service
public class RoleController extends BaseController<UserRole> {

    @Override
    protected Class<UserRole> getEntityClass() {
        return UserRole.class;
    }
}
