package com.bsu.server.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.dto.QThemeEntity;
import com.bsu.server.dto.ThemeEntity;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @Autowired
    private CourseGroupController courseGroupController;

    @Transactional(readOnly = true)
    public List<ThemeEntity> getThemesForCourse(Integer courseId, Integer userId) {
        if (courseId == null || userId == null) {
            return Collections.emptyList();
        }
        CourseGroupEntity themeInfo = courseGroupController.getAvailableThemesForUser(userId, courseId);
        return themeInfo.getAvailableThemes();
    }

    @Override
    protected Class<ThemeEntity> getEntityClass() {
        return ThemeEntity.class;
    }

    public List<ThemeEntity> getAllThemesInCourse(Integer courseId){
        JPQLQuery query = new JPAQuery(em);
        return query.from(QThemeEntity.themeEntity)
                .where(QThemeEntity.themeEntity.courseEntity.id.eq(courseId)).list(QThemeEntity.themeEntity);
    }
}
