package com.bsu.server.theoretic.themes.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.theoretic.themes.entity.QStudentStatusEntity;
import com.bsu.server.theoretic.themes.entity.StudentStatusEntity;
import com.google.common.collect.Lists;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class StudentStatusController extends BaseController<StudentStatusEntity> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected Class<StudentStatusEntity> getEntityClass() {
        return StudentStatusEntity.class;
    }

    public List<ThemeEntity> filterThemesByAccessibility(List<ThemeEntity> nonFiltered,
                                                         ThemeEntity.Significance significance, Integer userId) {
        return filterDependencies(nonFiltered, significance, userId);
    }

    private List<ThemeEntity> filterDependencies(List<ThemeEntity> filterData, ThemeEntity.Significance significance, Integer userId) {
        List<ThemeEntity> themeEntities = Lists.newArrayList();
        for (ThemeEntity theme : filterData) {
            boolean isAcceptable = true;
            if (theme.getRelatedTest() == null) {
                continue;
            }
            if (theme.getSignificance().lower(significance)) {
                continue;
            }
            for (ThemeEntity parentTheme : theme.getParentThemes()) {
                if (parentTheme.getSignificance().lowerEqual(significance)) {
                    StudentStatusEntity statusEntity = getByThemeId(parentTheme.getId(), userId);
                    if (statusEntity != null && statusEntity.getStatus() == StudentStatusEntity.Status.IN_PROGRESS) {
                        isAcceptable = false;
                        break;
                    }
                }
            }
            if (isAcceptable) {
                themeEntities.add(theme);
            }
        }
        return themeEntities;
    }

    private StudentStatusEntity getByThemeId(Integer parentThemeId, Integer userId) {
        JPQLQuery query = new JPAQuery(em);
        query = query.from(QStudentStatusEntity.studentStatusEntity).where(QStudentStatusEntity.studentStatusEntity
                .parentTheme.id.eq(parentThemeId).and(QStudentStatusEntity.studentStatusEntity.user.id.eq(userId)));
        if (query.count() > 0) {
            return query.singleResult(QStudentStatusEntity.studentStatusEntity);
        }
        return null;
    }
}
