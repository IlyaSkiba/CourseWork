package com.bsu.server.controller;

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
public class ThemeController {
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

    @Transactional(readOnly = true)
    public ThemeEntity getTheme(Integer selectedTopic) {
        TypedQuery<ThemeEntity> q = em.createQuery("from ThemeEntity where id = :themeId", ThemeEntity.class);
        q.setParameter("themeId", selectedTopic);
        return q.getSingleResult();
    }

    @Transactional(readOnly = false)
    public ThemeEntity createTheme(ThemeEntity entity) {
        em.persist(entity);
        em.flush();
        TypedQuery<ThemeEntity> q = em.createQuery("from ThemeEntity where id = :themeId", ThemeEntity.class);
        q.setParameter("themeId", entity.getId());
        return entity;
    }

    @Transactional(readOnly = false)
    public void updateEntity(ThemeEntity entity) {
        if (entity.getId() == null) {
            return;
        }
        em.merge(entity);
        em.flush();
    }

    @Transactional(readOnly = false)
    public boolean deleteTheme(Integer id) {
        ThemeEntity entity = getTheme(id);
        if (entity != null) {
            em.detach(entity);
            em.flush();
            return true;
        }
        return false;
    }
}
