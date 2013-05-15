package com.bsu.server.theoretic.themes.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.theoretic.themes.entity.StudentStatusEntity;
import com.google.common.collect.Lists;
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
                                                         ThemeEntity.Significance significance) {
        return filterDependencies(nonFiltered, significance);
    }

    private List<ThemeEntity> filterDependencies(List<ThemeEntity> filterData, ThemeEntity.Significance significance) {
        List<ThemeEntity> themeEntities = Lists.newArrayList();
        for (ThemeEntity theme : filterData) {
            boolean isAcceptable = true;
            for (ThemeEntity parentTheme : theme.getParentThemes()) {
                if (parentTheme.getSignificance().lower(significance)) {
                    StudentStatusEntity statusEntity = getById(parentTheme.getId());
                    if (statusEntity.getStatus() == StudentStatusEntity.Status.IN_PROGRESS) {
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
}
