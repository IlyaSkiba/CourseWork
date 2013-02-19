package com.bsu.server.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.dto.UserGroupEntity;
import com.bsu.server.dto.security.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : Ilya Skiba
 *         Date: 15.4.12
 *         Time: 10.20
 */

@Service
public class ThemeController extends BaseController<ThemeEntity> {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<ThemeEntity> getThemesForCourse(Integer courseId, Integer userId) {
        if (courseId == null || userId == null) {
            return Collections.emptyList();
        }
        TypedQuery<ThemeEntity> q = em.createQuery("from ThemeEntity where courseDto.id in (:courseId)", ThemeEntity.class);
        q.setParameter("courseId", Arrays.asList(courseId));
        List<ThemeEntity> queryResult = q.getResultList();
        UserAccount currentUser = em.createQuery("from UserAccount where id=:userId", UserAccount.class)
                .setParameter("userId", userId).getSingleResult();
        List<UserGroupEntity> userGroups = currentUser.getUserGroups();
        if (userGroups == null) {
            return Collections.emptyList();
        }

        ArrayList<ThemeEntity> result = new ArrayList<ThemeEntity>();
        for (UserGroupEntity userGroup : userGroups) {
            for (ThemeEntity theme : userGroup.getCourses().get(0).getAvailableThemes()) {
                if (queryResult.contains(theme)) {
                    result.add(theme);
                }
            }
        }
        return result;
    }

    @Override
    protected Class<ThemeEntity> getEntityClass() {
        return ThemeEntity.class;
    }
}
