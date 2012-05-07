package com.bsu.server.controller;

import com.bsu.server.dto.ThemeDto;
import com.bsu.server.dto.UserGroupDto;
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

    @Transactional(readOnly = false)
    public List<ThemeDto> getThemesForCourse(Integer courseId, Integer userId) {
        if (courseId == null || userId == null) return Collections.emptyList();
        TypedQuery<ThemeDto> q = em.createQuery("from ThemeDto where courseDto.id in (:courseId)", ThemeDto.class);
        q.setParameter("courseId", Arrays.asList(courseId));
        List<ThemeDto> queryResult = q.getResultList();
        UserAccount currentUser = em.createQuery("from UserAccount where id=:userId", UserAccount.class)
                .setParameter("userId", userId).getSingleResult();
        List<UserGroupDto> userGroups = currentUser.getUserGroups();
        if (userGroups == null) return Collections.emptyList();

        ArrayList<ThemeDto> result = new ArrayList<ThemeDto>();
        for (UserGroupDto userGroup : userGroups) {
            for (ThemeDto theme : userGroup.getAvailableThemes()) {
                if (queryResult.contains(theme)) {
                    result.add(theme);
                }
            }
        }
        return result;
    }

    @Transactional(readOnly = false)
    public ThemeDto getTheme(Integer selectedTopic) {
        TypedQuery<ThemeDto> q = em.createQuery("from ThemeDto where id = :themeId", ThemeDto.class);
        q.setParameter("themeId", selectedTopic);
        return q.getSingleResult();
    }
}
